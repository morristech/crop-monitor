package com.celpa.celpaapp.takecropphoto;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.Image;
import com.celpa.celpaapp.utils.AppSettings;
import com.celpa.celpaapp.utils.scheduler.BaseSchedulerProvider;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class TakeCropPhotoPresenter implements TakeCropPhotoContract.Presenter {

    private static final String TAG = TakeCropPhotoPresenter.class.getSimpleName();

    private TakeCropPhotoContract.View takePhotoView;

    private CompositeDisposable compositeDisposable;
    private BaseSchedulerProvider baseSchedulerProvider;
    private AppSettings appSettings;

    public TakeCropPhotoPresenter(TakeCropPhotoContract.View view,
                                  AppSettings settings,
                                  BaseSchedulerProvider schedulerProvider) {
        takePhotoView = view;
        compositeDisposable = new CompositeDisposable();
        baseSchedulerProvider = schedulerProvider;
        appSettings = settings;
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
        Flowable<String> flowable = Flowable.fromCallable(() -> {
            String filePath = takePhotoView.saveBitmapToStorage(img);
            return filePath;
        });

        takePhotoView.showLoadingDialog(takePhotoView.getParsingImageText());
        Disposable disposable = flowable
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(filePath -> {
                            // Add location and weather
                            Crop crop = new Crop();
                            crop.img.add(new Image(filePath));
                            takePhotoView.hideLoadingDialog();
                            takePhotoView.goToAddCropDetails(crop);
                        },
                        throwable -> {
                            takePhotoView.showLoadingDialog(throwable.getMessage());
                        }
                );

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
