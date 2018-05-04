package midien.kheldiente.cma.login;


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

import midien.kheldiente.cma.R;
import midien.kheldiente.cma.common.LoadingDialog;
import midien.kheldiente.cma.common.OkDialog;
import midien.kheldiente.cma.register.RegisterFarmerActivity;
import midien.kheldiente.cma.takecropphoto.TakeCropPhotoActivity;
import midien.kheldiente.cma.utils.ActivityUtils;

public class LoginFragment extends Fragment
        implements LoginContract.View,
        View.OnClickListener {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private LoginContract.Presenter presenter;

    private EditText userNameEditTxt;
    private EditText passwordEditTxt;
    private Button loginBtn;
    private Button registerBtn;
    private LoadingDialog loadingDialog;
    private OkDialog okDialog;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        userNameEditTxt = root.findViewById(R.id.edittext_username);
        passwordEditTxt = root.findViewById(R.id.edittext_password);
        loginBtn = root.findViewById(R.id.btn_login);
        registerBtn = root.findViewById(R.id.btn_register);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        return root;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoggingInDialog() {
        loadingDialog = LoadingDialog.newInstance(getString(R.string.please_wait));
        loadingDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public void hideLoggingInDialog() {
        loadingDialog.dismiss();
    }

    @Override
    public void goToTakePhoto() {
        presenter.unsubscribe();
        getActivity().finish();
        ActivityUtils.goToActivity(getActivity(), TakeCropPhotoActivity.class);
    }

    @Override
    public void showOkDialog(String msg) {
        okDialog = OkDialog.newInstance(msg);
        okDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public String setFailedToLoginText() {
        return getString(R.string.invalid_login);
    }

    @Override
    public void goToRegistration() {
        Intent intent = new Intent(getActivity(), RegisterFarmerActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                presenter.login(userNameEditTxt.getText().toString(), passwordEditTxt.getText().toString());
                break;
            case R.id.btn_register:
                goToRegistration();
                break;
            default:
                break;
        }
    }
}
