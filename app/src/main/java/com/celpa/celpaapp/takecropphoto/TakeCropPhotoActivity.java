package com.celpa.celpaapp.takecropphoto;

import android.os.Bundle;

import com.celpa.celpaapp.BaseActivity;
import com.celpa.celpaapp.R;
import com.celpa.celpaapp.utils.ActivityUtils;
import com.celpa.celpaapp.utils.AppSettings;
import com.celpa.celpaapp.utils.scheduler.SchedulerProvider;

public class TakeCropPhotoActivity extends BaseActivity {

    private static final String TAG = TakeCropPhotoActivity.class.getSimpleName();

    private TakeCropPhotoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takecropphoto);

        TakeCropPhotoFragment takeCropPhotoFragment = (TakeCropPhotoFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if(takeCropPhotoFragment == null) {
            // Create the fragment
            takeCropPhotoFragment = TakeCropPhotoFragment.newInstance();
            ActivityUtils.addFragmentActivity(getSupportFragmentManager(), takeCropPhotoFragment, R.id.content_frame);
        }

        presenter = new TakeCropPhotoPresenter(takeCropPhotoFragment, AppSettings.getInstance(this), SchedulerProvider.getInstance());
    }
}
