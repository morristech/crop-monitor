package com.celpa.celpaapp.takecropphoto;


import android.graphics.Bitmap;

import com.celpa.celpaapp.BasePresenter;
import com.celpa.celpaapp.BaseView;

public interface TakeCropPhotoContract {

    interface View extends BaseView<Presenter> {

        boolean takePhoto();

    }

    interface Presenter extends BasePresenter {

        Bitmap getBitmap();

        String getLocation();

        void getWeather();

    }

}
