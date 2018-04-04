package com.celpa.celpaapp.login;


import com.celpa.celpaapp.BasePresenter;
import com.celpa.celpaapp.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void showLoadingDialog();

    }

    interface Presenter extends BasePresenter {

        boolean isValidUsername(String userName);

        boolean isValidPassword(String password);

        void login();

    }

}
