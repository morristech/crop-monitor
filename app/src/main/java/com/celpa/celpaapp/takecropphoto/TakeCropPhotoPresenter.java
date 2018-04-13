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
import com.celpa.celpaapp.utils.weather.OpenWeather;
import com.celpa.celpaapp.utils.weather.OpenWeatherHelper;
import com.google.gson.JsonObject;

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
    private OpenWeather weather;

    public TakeCropPhotoPresenter(TakeCropPhotoContract.View view,
                                  AppSettings settings,
                                  CropLocationHelper locationHelper,
                                  OpenWeather openWeather,
                                  BaseSchedulerProvider schedulerProvider) {
        takePhotoView = view;
        compositeDisposable = new CompositeDisposable();
        baseSchedulerProvider = schedulerProvider;
        appSettings = settings;
        cropLocationHelper = locationHelper;
        weather = openWeather;
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
    public Flowable<Location> getLocation() {
        return cropLocationHelper.getLastLocation();
    }

    @Override
    public Flowable<JsonObject> getWeather(double lat, double lon) {
        return weather.getWeatherFromCoords(lat, lon);
    }

    @Override
    public void processPhoto(byte[] img) {
        compositeDisposable.clear();

        Flowable<String> saveBitmapToStorage = Flowable.fromCallable(() -> {
            String filePath = takePhotoView.saveBitmapToStorage(img);
            return filePath;
        });

        takePhotoView.showLoadingDialog(takePhotoView.getGettingLocationText());
        Disposable disposable = getLocation()
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .flatMap(location -> cropLocationHelper.getRxLocation().geocoding().fromLocation(location).toFlowable())
                .flatMap(address -> {
                    crop.location = Crop.convertLocationToJsonObject(address).toString();
                    takePhotoView.updateLoadingDialog(takePhotoView.getGettingWeatherText());
                    return getWeather(address.getLatitude(), address.getLongitude())
                            // Prevent network main thread exception
                            .subscribeOn(baseSchedulerProvider.io())
                            .observeOn(baseSchedulerProvider.ui())
                            ;
                })
                .flatMap(jsonObject -> {
                    crop.weather = OpenWeather.getWeather(jsonObject);
                    takePhotoView.updateLoadingDialog(takePhotoView.getParsingImageText());
                    return saveBitmapToStorage;
                })
                .subscribe(filePath -> {
                            unsubscribe();
                            // Reset list of images stored!
                            crop.img = new ArrayList<>(0);
                            crop.img.add(new Image(filePath));
                            takePhotoView.hideLoadingDialog();
                            takePhotoView.goToAddCropDetails(crop);
                        },
                        throwable -> {
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
                        unsubscribe();
                        takePhotoView.closeMe();
                        takePhotoView.goToLogin();
                        takePhotoView.hideLoadingDialog();
                    },
                    throwable -> takePhotoView.hideLoadingDialog()
                );
        compositeDisposable.add(disposable);
    }

}
