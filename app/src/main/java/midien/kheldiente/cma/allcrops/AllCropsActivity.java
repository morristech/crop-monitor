package midien.kheldiente.cma.allcrops;


import android.os.Bundle;
import android.support.annotation.Nullable;

import midien.kheldiente.cma.BaseActivity;
import midien.kheldiente.cma.R;
import midien.kheldiente.cma.data.source.remote.CropRemoteDataSource;
import midien.kheldiente.cma.utils.ActivityUtils;
import midien.kheldiente.cma.utils.scheduler.BaseSchedulerProvider;
import midien.kheldiente.cma.utils.scheduler.SchedulerProvider;

public class AllCropsActivity extends BaseActivity {

    private AllCropsPresenter presenter;
    private CropRemoteDataSource remoteDataSource;
    private BaseSchedulerProvider schedulerProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcrops);

        AllCropsFragment allCropsFragment = (AllCropsFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if(allCropsFragment == null) {
            // Create the fragment
            allCropsFragment = AllCropsFragment.newInstance();
            ActivityUtils.addFragmentActivity(getSupportFragmentManager(), allCropsFragment, R.id.content_frame);
        }

        remoteDataSource = CropRemoteDataSource.getInstance(this);
        schedulerProvider = SchedulerProvider.getInstance();
        presenter = new AllCropsPresenter(allCropsFragment, remoteDataSource, schedulerProvider);
    }
}
