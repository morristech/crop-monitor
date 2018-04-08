package com.celpa.celpaapp.login;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.celpa.celpaapp.BaseActivity;
import com.celpa.celpaapp.R;
import com.celpa.celpaapp.utils.ActivityUtils;
import com.celpa.celpaapp.utils.scheduler.SchedulerProvider;

public class LoginActivity extends BaseActivity {

    private LoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if(loginFragment == null) {
            // Create the fragment
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentActivity(getSupportFragmentManager(), loginFragment, R.id.content_frame);
        }

        presenter = new LoginPresenter(loginFragment, SchedulerProvider.getInstance());
    }
}
