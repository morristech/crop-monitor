package com.celpa.celpaapp.takephoto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.celpa.celpaapp.BaseActivity;
import com.celpa.celpaapp.R;
import com.celpa.celpaapp.utils.ActivityUtils;

public class TakePhotoActivity extends BaseActivity {

    private static final String TAG = TakePhotoActivity.class.getSimpleName();

    private TakePhotoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takephoto);

        TakePhotoFragment takePhotoFragment = (TakePhotoFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if(takePhotoFragment == null) {
            // Create the fragment
            takePhotoFragment = TakePhotoFragment.newInstance();
            ActivityUtils.addFragmentActivity(getSupportFragmentManager(), takePhotoFragment, R.id.content_frame);
        }

        presenter = new TakePhotoPresenter(takePhotoFragment);
    }
}
