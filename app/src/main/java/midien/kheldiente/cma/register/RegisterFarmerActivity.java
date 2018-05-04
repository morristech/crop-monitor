package midien.kheldiente.cma.register;


import android.os.Bundle;
import android.support.annotation.Nullable;

import midien.kheldiente.cma.BaseActivity;
import midien.kheldiente.cma.R;
import midien.kheldiente.cma.data.source.remote.FarmerRemoteDataSource;
import midien.kheldiente.cma.utils.ActivityUtils;
import midien.kheldiente.cma.utils.AppSettings;
import midien.kheldiente.cma.utils.scheduler.BaseSchedulerProvider;
import midien.kheldiente.cma.utils.scheduler.SchedulerProvider;

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
