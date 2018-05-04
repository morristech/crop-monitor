package midien.kheldiente.cma.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import midien.kheldiente.cma.BaseActivity;
import midien.kheldiente.cma.R;
import midien.kheldiente.cma.takecropphoto.TakeCropPhotoActivity;
import midien.kheldiente.cma.utils.ActivityUtils;
import midien.kheldiente.cma.utils.AppSettings;
import midien.kheldiente.cma.utils.scheduler.SchedulerProvider;

public class LoginActivity extends BaseActivity {

    private LoginPresenter presenter;

    private AppSettings appSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        appSettings = AppSettings.getInstance(this);

        if(appSettings.isLoggedIn()) {
            goToTakePhoto();
        } else {
            LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
            if (loginFragment == null) {
                // Create the fragment
                loginFragment = LoginFragment.newInstance();
                ActivityUtils.addFragmentActivity(getSupportFragmentManager(), loginFragment, R.id.content_frame);
            }

            presenter = new LoginPresenter(loginFragment, appSettings, SchedulerProvider.getInstance());
        }
    }

    private void goToTakePhoto() {
        finish();
        Intent intent = new Intent(this, TakeCropPhotoActivity.class);
        startActivity(intent);
    }
}
