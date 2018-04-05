package com.celpa.celpaapp.takecropphoto;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.celpa.celpaapp.data.Crop;

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

    @Override
    public void processPhoto(byte[] photoByte) {
        // Add location and weather
        Crop crop = new Crop();
        crop.img = photoByte;
        takePhotoView.goToAddCropDetails(crop);
    }
}
