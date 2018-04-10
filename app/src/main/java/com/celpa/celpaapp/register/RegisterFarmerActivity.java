package com.celpa.celpaapp.register;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.celpa.celpaapp.BaseActivity;
import com.celpa.celpaapp.R;
import com.celpa.celpaapp.data.source.remote.FarmerRemoteDataSource;
import com.celpa.celpaapp.utils.ActivityUtils;
import com.celpa.celpaapp.utils.AppSettings;
import com.celpa.celpaapp.utils.scheduler.BaseSchedulerProvider;
import com.celpa.celpaapp.utils.scheduler.SchedulerProvider;

public class RegisterFarmerActivity extends BaseActivity {

    private RegisterFarmerPresenter presenter;
    private BaseSchedulerProvider provider;
    private FarmerRemoteDataSource remoteDataSource;
    private AppSettings appSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterFarmerFragment registerFarmerFragment = (RegisterFarmerFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if(registerFarmerFragment == null) {
            // Create the fragment
            registerFarmerFragment = RegisterFarmerFragment.newInstance();
            ActivityUtils.addFragmentActivity(getSupportFragmentManager(), registerFarmerFragment, R.id.content_frame);
        }

        appSettings = AppSettings.getInstance(this);
        provider = SchedulerProvider.getInstance();
        remoteDataSource = new FarmerRemoteDataSource();
        presenter = new RegisterFarmerPresenter(registerFarmerFragment,
                        remoteDataSource,
                        appSettings,
                        provider
                        );
    }
}
