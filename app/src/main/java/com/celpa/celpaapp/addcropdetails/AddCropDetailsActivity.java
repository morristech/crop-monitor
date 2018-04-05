package com.celpa.celpaapp.addcropdetails;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.celpa.celpaapp.BaseActivity;
import com.celpa.celpaapp.R;
import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.utils.ActivityUtils;

public class AddCropDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcropdetails);

        AddCropDetailsFragment addCropDetailsFragment = (AddCropDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if(addCropDetailsFragment == null) {
            // Create the fragment
            addCropDetailsFragment = AddCropDetailsFragment.newInstance(new Crop());
            ActivityUtils.addFragmentActivity(getSupportFragmentManager(), addCropDetailsFragment, R.id.content_frame);
        }
    }
}
