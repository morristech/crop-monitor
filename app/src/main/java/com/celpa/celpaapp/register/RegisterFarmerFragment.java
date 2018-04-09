package com.celpa.celpaapp.register;


import android.support.v4.app.Fragment;

import com.celpa.celpaapp.common.OkDialog;

public class RegisterFarmerFragment extends Fragment
        implements RegisterFarmerContract.View {

    private static final String TAG = RegisterFarmerFragment.class.getSimpleName();

    private RegisterFarmerContract.Presenter presenter;

    @Override
    public void setPresenter(RegisterFarmerContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void hideLoadingDialog() {

    }

    @Override
    public void showOkDialog(String msg) {

    }

    @Override
    public void showOkDialog(String msg, OkDialog.EventListener listener) {

    }

    @Override
    public void goToTakePhoto() {

    }

    @Override
    public void closeMe() {

    }
}
