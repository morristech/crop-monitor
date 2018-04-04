package com.celpa.celpaapp.takephoto;


import android.graphics.Bitmap;

import com.celpa.celpaapp.BasePresenter;
import com.celpa.celpaapp.BaseView;

public interface TakePhotoContract {

    interface View extends BaseView<Presenter> {

        boolean takePhoto();

    }

    interface Presenter extends BasePresenter {

        Bitmap getBitmap();

        String getLocation();

        void getWeather();

    }

}
