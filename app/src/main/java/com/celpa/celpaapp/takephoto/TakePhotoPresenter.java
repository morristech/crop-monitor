package com.celpa.celpaapp.takephoto;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;

public class TakePhotoPresenter implements TakePhotoContract.Presenter {

    TakePhotoContract.View takePhotoView;

    public TakePhotoPresenter(@NonNull TakePhotoContract.View view) {
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
