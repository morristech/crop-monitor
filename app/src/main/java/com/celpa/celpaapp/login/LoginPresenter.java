package com.celpa.celpaapp.login;

import android.util.Log;

import com.celpa.celpaapp.data.source.remote.CelpaApiHelper;
import com.celpa.celpaapp.data.source.remote.CelpaApiService;
import com.celpa.celpaapp.data.source.remote.FarmerRemoteDataSource;
import com.celpa.celpaapp.utils.scheduler.BaseSchedulerProvider;
import com.google.gson.JsonObject;


import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private final BaseSchedulerProvider schedulerProvider;

    private CompositeDisposable compositeDisposable;

    private FarmerRemoteDataSource remoteDataSource;

    LoginContract.View loginView;

    public LoginPresenter(LoginContract.View view, BaseSchedulerProvider baseSchedulerProvider) {
        loginView = view;
        schedulerProvider = baseSchedulerProvider;
        compositeDisposable = new CompositeDisposable();
        remoteDataSource = FarmerRemoteDataSource.getInstance();
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

            compositeDisposable.clear();
            Disposable disposable = remoteDataSource.getFarmer(userName, password)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(json -> {
                                loginView.hideLoggingInDialog();
                                // onNext
                                long id = json.getAsJsonPrimitive("id").getAsLong();
                                Log.d(TAG, String.valueOf(id));
                                if (id > 0)
                                    loginView.goToTakePhoto();
                                else
                                    loginView.showOkDialog(loginView.setFailedToLoginText());
                            },
                            throwable -> {
                                loginView.hideLoggingInDialog();
                                loginView.showOkDialog(throwable.getMessage());
                            }
                    );

            compositeDisposable.add(disposable);
        } else {
            loginView.showOkDialog(loginView.setFailedToLoginText());
        }
    }

}
