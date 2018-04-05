package com.celpa.celpaapp.takecropphoto;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;

public class TakeCropPhotoPresenter implements TakeCropPhotoContract.Presenter {

    TakeCropPhotoContract.View takePhotoView;

    public TakeCropPhotoPresenter(@NonNull TakeCropPhotoContract.View view) {
        takePhotoView = view;
        takePhotoView.setPresenter(this);
    }

    @Override
    public void onCreate() {

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
}
