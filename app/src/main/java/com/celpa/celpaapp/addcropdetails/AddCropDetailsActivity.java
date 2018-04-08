package com.celpa.celpaapp.addcropdetails;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.celpa.celpaapp.BaseActivity;
import com.celpa.celpaapp.R;
import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.source.local.CropLocalDataSource;
import com.celpa.celpaapp.data.source.remote.CropRemoteDataSource;
import com.celpa.celpaapp.utils.ActivityUtils;
import com.celpa.celpaapp.utils.scheduler.BaseSchedulerProvider;
import com.celpa.celpaapp.utils.scheduler.SchedulerProvider;

public class AddCropDetailsActivity extends BaseActivity {

    private AddCropDetailsPresenter presenter;
    private BaseSchedulerProvider provider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcropdetails);

        AddCropDetailsFragment addCropDetailsFragment = (AddCropDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if(addCropDetailsFragment == null) {
            // Create the fragment
            addCropDetailsFragment = AddCropDetailsFragment.newInstance();
            ActivityUtils.addFragmentActivity(getSupportFragmentManager(), addCropDetailsFragment, R.id.content_frame);
        }

        provider = SchedulerProvider.getInstance();
        presenter = new AddCropDetailsPresenter(addCropDetailsFragment,
                provider,
                CropLocalDataSource.getInstance(this, provider),
                CropRemoteDataSource.getInstance(this));
    }
}
