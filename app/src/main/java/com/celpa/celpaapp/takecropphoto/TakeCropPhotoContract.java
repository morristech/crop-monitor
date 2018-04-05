package com.celpa.celpaapp.takecropphoto;


import android.graphics.Bitmap;
import android.os.Bundle;

import com.celpa.celpaapp.BasePresenter;
import com.celpa.celpaapp.BaseView;
import com.celpa.celpaapp.data.Crop;

public interface TakeCropPhotoContract {

    interface View extends BaseView<Presenter> {

        boolean takePhoto();

        void goToAddCropDetails();

        void goToAddCropDetails(Crop crop);

    }

    interface Presenter extends BasePresenter {

        Bitmap getBitmap();

        String getLocation();

        void getWeather();

        void processPhoto(byte[] photo);

    }

}
