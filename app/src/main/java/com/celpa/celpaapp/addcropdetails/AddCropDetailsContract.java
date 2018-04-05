package com.celpa.celpaapp.addcropdetails;


import com.celpa.celpaapp.BasePresenter;
import com.celpa.celpaapp.BaseView;
import com.celpa.celpaapp.data.Crop;

public interface AddCropDetailsContract {

    interface View extends BaseView<Presenter> {

        void showCapturedPhoto();

        void showLocation();

        void showWeather();

        void showLoadingDialog();

    }

    interface Presenter extends BasePresenter {

        void saveCropDetailsInLocal(Crop crop);

        void saveCropDetailsInRemote(Crop crop);

        void saveCropDetails(Crop crop);

        boolean isValidInput(String input);

    }

}
