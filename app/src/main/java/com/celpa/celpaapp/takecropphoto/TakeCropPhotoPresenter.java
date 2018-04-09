package com.celpa.celpaapp.takecropphoto;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.Image;
import com.celpa.celpaapp.utils.scheduler.BaseSchedulerProvider;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class TakeCropPhotoPresenter implements TakeCropPhotoContract.Presenter {

    private static final String TAG = TakeCropPhotoPresenter.class.getSimpleName();

    private TakeCropPhotoContract.View takePhotoView;

    private CompositeDisposable compositeDisposable;
    private BaseSchedulerProvider baseSchedulerProvider;

    public TakeCropPhotoPresenter(TakeCropPhotoContract.View view,
                                  BaseSchedulerProvider schedulerProvider) {
        takePhotoView = view;
        compositeDisposable = new CompositeDisposable();
        baseSchedulerProvider = schedulerProvider;
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
}
