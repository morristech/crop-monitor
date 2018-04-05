package com.celpa.celpaapp.login;

public class LoginPresenter implements LoginContract.Presenter {

    LoginContract.View loginView;

    public LoginPresenter(LoginContract.View view) {
        loginView = view;
        loginView.setPresenter(this);
    }

    @Override
    public void onCreate() {}

    @Override
    public boolean isValidUsername(String userName) {
        return !userName.isEmpty();
    }

    @Override
    public boolean isValidPassword(String password) {
        return !password.isEmpty();
    }

    @Override
    public void login(String userName, String userPassword) {
        loginView.showLoggingInDialog();
        loginView.hideLoggingInDialog();

        boolean isValid = isValidUsername(userName) && isValidPassword(userPassword);
        loginView.goToTakePhoto();
    }

    @Override
    public void register() {}
}
