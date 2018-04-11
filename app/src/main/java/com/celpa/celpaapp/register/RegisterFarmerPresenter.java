package com.celpa.celpaapp.register;


import android.util.Log;

import com.celpa.celpaapp.common.OkDialog;
import com.celpa.celpaapp.data.Farmer;
import com.celpa.celpaapp.data.source.remote.FarmerRemoteDataSource;
import com.celpa.celpaapp.utils.AppSettings;
import com.celpa.celpaapp.utils.scheduler.BaseSchedulerProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RegisterFarmerPresenter implements RegisterFarmerContract.Presenter {

    private static final String TAG = RegisterFarmerPresenter.class.getSimpleName();

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    private RegisterFarmerContract.View registerFarmerView;
    private FarmerRemoteDataSource remoteDataSource;
    private BaseSchedulerProvider schedulerProvider;
    private AppSettings appSettings;
    private CompositeDisposable compositeDisposable;

    public RegisterFarmerPresenter(RegisterFarmerContract.View view,
                                   FarmerRemoteDataSource remote,
                                   AppSettings settings,
                                   BaseSchedulerProvider provider) {
        registerFarmerView = view;
        remoteDataSource = remote;
        appSettings = settings;
        schedulerProvider = provider;
        compositeDisposable = new CompositeDisposable();
        registerFarmerView.setPresenter(this);
    }

    @Override
    public void onCreate() {}

    @Override
    public void subscribe() {}

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public boolean isValid(Farmer farmer) {
        return !farmer.firstName.isEmpty()
                && !farmer.lastName.isEmpty()
                && !farmer.email.isEmpty()
                && !farmer.userName.isEmpty()
                && !farmer.password.isEmpty()
                ;
    }

    @Override
    public boolean isValidEmail(String email) {
        return !email.isEmpty() && VALID_EMAIL_ADDRESS_REGEX .matcher(email).find();
    }

    @Override
    public void registerFarmer(Farmer farmer) {
        boolean isValid = isValid(farmer);
        boolean isValidEmail = isValidEmail(farmer.email);
        if(isValid && isValidEmail) {
            registerFarmerView.showLoadingDialog(registerFarmerView.getRegisterFarmerTxt());
            compositeDisposable.clear();
            Disposable disposable = remoteDataSource.registerFarmer(farmer)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(json -> {
                                unsubscribe();
                                Log.d(TAG, json.toString());
                                long id = json.getAsJsonPrimitive("id").getAsLong();
                                appSettings.setFarmer(id);
                                registerFarmerView.hideLoadingDialog();
                                registerFarmerView.showOkDialog(registerFarmerView.getRegistrationSuccessfulTxt(),
                                        () -> {
                                            registerFarmerView.closeMe();
                                            registerFarmerView.goToTakePhoto();
                                        });
                            },
                            throwable -> {
                                unsubscribe();
                                registerFarmerView.showOkDialog(throwable.getMessage());
                            }
                    );
            compositeDisposable.add(disposable);
        } else {
            registerFarmerView.showOkDialog(registerFarmerView.getPleaseFillUpAllFieldsTxt());
        }
    }

    @Override
    public boolean isSamePassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
