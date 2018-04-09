package com.celpa.celpaapp.register;


import com.celpa.celpaapp.BasePresenter;
import com.celpa.celpaapp.BaseView;
import com.celpa.celpaapp.common.OkDialog;
import com.celpa.celpaapp.data.Farmer;

public interface RegisterFarmerContract {

    interface View extends BaseView<Presenter> {

        void showLoadingDialog(String msg);

        void hideLoadingDialog();

        void showOkDialog(String msg);

        void showOkDialog(String msg, OkDialog.EventListener listener);

        void goToTakePhoto();

        void closeMe();

    }

    interface Presenter extends BasePresenter {

        boolean isValid(Farmer farmer);

        void registerFarmer(Farmer farmer);
    }
}
