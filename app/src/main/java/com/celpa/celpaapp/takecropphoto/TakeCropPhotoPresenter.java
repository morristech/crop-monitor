package com.celpa.celpaapp.takecropphoto;


import android.graphics.Bitmap;
import android.location.Address;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.Image;
import com.celpa.celpaapp.utils.AppSettings;
import com.celpa.celpaapp.utils.CropLocationHelper;
import com.celpa.celpaapp.utils.scheduler.BaseSchedulerProvider;

import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class TakeCropPhotoPresenter implements TakeCropPhotoContract.Presenter {

    private static final String TAG = TakeCropPhotoPresenter.class.getSimpleName();

    private TakeCropPhotoContract.View takePhotoView;

    private Crop crop = new Crop();

    private CompositeDisposable compositeDisposable;
    private BaseSchedulerProvider baseSchedulerProvider;
    private AppSettings appSettings;
    private CropLocationHelper cropLocationHelper;

    public TakeCropPhotoPresenter(TakeCropPhotoContract.View view,
                                  AppSettings settings,
                                  CropLocationHelper locationHelper,
                                  BaseSchedulerProvider schedulerProvider) {
        takePhotoView = view;
        compositeDisposable = new CompositeDisposable();
        baseSchedulerProvider = schedulerProvider;
        appSettings = settings;
        cropLocationHelper = locationHelper;
        takePhotoView.setPresenter(this);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    @Override
    public String getLocation() {
        return null;
    }

    @Override
    public void getWeather() {

    }

    @Override
    public void processPhoto(byte[] img) {
        compositeDisposable.clear();

        Flowable<String> saveBitmapToStorage = Flowable.fromCallable(() -> {
            String filePath = takePhotoView.saveBitmapToStorage(img);
            return filePath;
        });
        Flowable<Location> getLastLocation = cropLocationHelper.getLastLocation();

        takePhotoView.showLoadingDialog(takePhotoView.getParsingImageText());
        Disposable disposable = getLastLocation
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .flatMap(location -> cropLocationHelper.getRxLocation().geocoding().fromLocation(location).toFlowable())
                .flatMap(address -> {
                    crop.location = Crop.convertLocationToJsonObject(address).toString();
                    return saveBitmapToStorage;
                })
                .subscribe(filePath -> {
                            unsubscribe();
                            crop.img.add(new Image(filePath));
                            takePhotoView.hideLoadingDialog();
                            takePhotoView.goToAddCropDetails(crop);
                        },
                        throwable -> {
                            unsubscribe();
                            takePhotoView.hideLoadingDialog();
                            takePhotoView.showOkDialog(throwable.toString());
                        }
                )
                ;

        compositeDisposable.add(disposable);
    }

    @Override
    public void clearCacheAndLogout() {
        compositeDisposable.clear();
        takePhotoView.showLoadingDialog(takePhotoView.getLoggingOutText());
        Disposable disposable = appSettings.clearAll()
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(result -> {
                        takePhotoView.closeMe();
                        takePhotoView.goToLogin();
                        takePhotoView.hideLoadingDialog();
                    },
                    throwable -> takePhotoView.hideLoadingDialog()
                );
        compositeDisposable.add(disposable);
    }

}
