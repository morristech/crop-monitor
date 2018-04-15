package com.celpa.celpaapp.addcropdetails;


import com.celpa.celpaapp.BasePresenter;
import com.celpa.celpaapp.BaseView;
import com.celpa.celpaapp.common.OkDialog;
import com.celpa.celpaapp.data.Crop;

public interface AddCropDetailsContract {

    interface View extends BaseView<Presenter> {

        void showCapturedPhoto();

        void showLocation();

        void showWeather();

        void showLoadingDialog();

        void hideLoadingDialog();

        void showDatePickerDialog();

        void setPlantedStartDate(String dateOfHarvest);

        void showOkDialog(String msg);

        void showOkDialog(String msg, OkDialog.EventListener listener);

        String getCropSavedText();

        void closeEditor();

    }

    interface Presenter extends BasePresenter {

        void saveCropDetails(Crop crop);

        boolean isValidInput(String input);

    }

}
