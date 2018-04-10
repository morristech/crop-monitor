package com.celpa.celpaapp.login;


import com.celpa.celpaapp.BasePresenter;
import com.celpa.celpaapp.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void showLoggingInDialog();

        void hideLoggingInDialog();

        void goToTakePhoto();

        void showOkDialog(String msg);

        String setFailedToLoginText();

        void goToRegistration();

    }

    interface Presenter extends BasePresenter {

        boolean isValidUsername(String userName);

        boolean isValidPassword(String password);

        void login(String userName, String password);

        void saveId(long farmerId);

    }

}
