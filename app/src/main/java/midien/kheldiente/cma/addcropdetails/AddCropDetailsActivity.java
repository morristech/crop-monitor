package midien.kheldiente.cma.addcropdetails;


import android.os.Bundle;
import android.support.annotation.Nullable;

import midien.kheldiente.cma.BaseActivity;
import midien.kheldiente.cma.R;

import midien.kheldiente.cma.data.source.local.CropLocalDataSource;
import midien.kheldiente.cma.data.source.remote.CropRemoteDataSource;
import midien.kheldiente.cma.utils.ActivityUtils;
import midien.kheldiente.cma.utils.scheduler.BaseSchedulerProvider;
import midien.kheldiente.cma.utils.scheduler.SchedulerProvider;

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
