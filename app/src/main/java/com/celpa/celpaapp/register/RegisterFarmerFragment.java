package com.celpa.celpaapp.register;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.celpa.celpaapp.R;
import com.celpa.celpaapp.common.LoadingDialog;
import com.celpa.celpaapp.common.OkDialog;
import com.celpa.celpaapp.data.Farmer;
import com.celpa.celpaapp.takecropphoto.TakeCropPhotoActivity;

public class RegisterFarmerFragment extends Fragment
        implements RegisterFarmerContract.View,
        View.OnClickListener {

    private static final String TAG = RegisterFarmerFragment.class.getSimpleName();

    private static RegisterFarmerFragment INSTANCE;

    private RegisterFarmerContract.Presenter presenter;

    private LoadingDialog loadingDialog;
    private OkDialog okDialog;
    private EditText firstNameEdittxt;
    private EditText lastNameEdittxt;
    private EditText emailEdittxt;
    private EditText userNameEdittxt;
    private EditText passwordEdittxt;
    private EditText confirmPasswordEdittxt;
    private Button registerBtn;

    public static RegisterFarmerFragment newInstance() {
        if(INSTANCE == null) {
            INSTANCE = new RegisterFarmerFragment();
        }
        return INSTANCE;
    }

    @Override
    public void setPresenter(RegisterFarmerContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        firstNameEdittxt = root.findViewById(R.id.edittext_firstName);
        lastNameEdittxt = root.findViewById(R.id.edittext_lastName);
        emailEdittxt = root.findViewById(R.id.edittext_email);
        userNameEdittxt = root.findViewById(R.id.edittext_username);
        passwordEdittxt = root.findViewById(R.id.edittext_password);
        registerBtn = root.findViewById(R.id.btn_register);
        confirmPasswordEdittxt = root.findViewById(R.id.edittext_conf_password);

        registerBtn.setOnClickListener(this);

        return root;
    }

    @Override
    public void showLoadingDialog(String msg) {
        loadingDialog = LoadingDialog.newInstance(msg);
        loadingDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public void hideLoadingDialog() {
        if(loadingDialog == null)
            return;
        loadingDialog.dismiss();
    }

    @Override
    public void showOkDialog(String msg) {
        okDialog = OkDialog.newInstance(msg);
        okDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public void showOkDialog(String msg, OkDialog.EventListener listener) {
        okDialog = OkDialog.newInstance(msg, listener);
        okDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public String getRegisterFarmerTxt() {
        return getString(R.string.registering);
    }

    @Override
    public String getPleaseFillUpAllFieldsTxt() {
        return getString(R.string.fill_up_all_fields);
    }

    @Override
    public String getRegistrationSuccessfulTxt() {
        return getString(R.string.registered_farmer);
    }

    @Override
    public void goToTakePhoto() {
        Intent intent = new Intent(getActivity(), TakeCropPhotoActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void closeMe() {
        getActivity().finish();
    }

    private Farmer generateFarmerDetails() {
        Farmer farmer = new Farmer();
        farmer.firstName = firstNameEdittxt.getText().toString();
        farmer.lastName = lastNameEdittxt.getText().toString();
        farmer.email = emailEdittxt.getText().toString();
        farmer.userName = userNameEdittxt.getText().toString();
        farmer.password = passwordEdittxt.getText().toString();

        return farmer;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                if(presenter.isSamePassword(passwordEdittxt.getText().toString(),
                                        confirmPasswordEdittxt.getText().toString())) {
                    presenter.registerFarmer(generateFarmerDetails());
                } else {
                    showOkDialog(getString(R.string.confirm_password_fail));
                }
                break;
            default:
                break;
        }
    }
}
