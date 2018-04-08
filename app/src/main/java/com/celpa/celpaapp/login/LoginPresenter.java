package com.celpa.celpaapp.login;

import android.util.Log;

import com.celpa.celpaapp.data.source.remote.CelpaApiHelper;
import com.celpa.celpaapp.data.source.remote.CelpaApiService;
import com.celpa.celpaapp.utils.scheduler.BaseSchedulerProvider;
import com.google.gson.JsonObject;


import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private final BaseSchedulerProvider schedulerProvider;

    private CompositeDisposable compositeDisposable;

    LoginContract.View loginView;

    public LoginPresenter(LoginContract.View view, BaseSchedulerProvider baseSchedulerProvider) {
        loginView = view;
        schedulerProvider = baseSchedulerProvider;
        compositeDisposable = new CompositeDisposable();

        loginView.setPresenter(this);
    }

    @Override
    public void onCreate() {}

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public boolean isValidUsername(String userName) {
        return !userName.isEmpty();
    }

    @Override
    public boolean isValidPassword(String password) {
        return !password.isEmpty();
    }

    @Override
    public void login(String userName, String password) {
        Log.d(TAG, "Logging in...");
        boolean isValid = isValidUsername(userName) && isValidPassword(password);
        if (isValid) {
            loginView.showLoggingInDialog();

            CelpaApiService celpaApiService = CelpaApiHelper.getApiInstance();
            Flowable<JsonObject> flowable = celpaApiService.isFarmerRegistered(userName, password)
                    .singleElement()
                    .toFlowable();

            compositeDisposable.clear();
            Disposable disposable = flowable.subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(json -> {
                                loginView.hideLoggingInDialog();
                                // onNext
                                boolean success = json.getAsJsonPrimitive("success").getAsBoolean();
                                if (success)
                                    loginView.goToTakePhoto();
                            },
                            throwable -> loginView.hideLoggingInDialog()
                    );

            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void register() {}
}
