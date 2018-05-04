package midien.kheldiente.cma.takecropphoto;

import android.os.Bundle;

import midien.kheldiente.cma.BaseActivity;
import midien.kheldiente.cma.R;
import midien.kheldiente.cma.utils.ActivityUtils;
import midien.kheldiente.cma.utils.AppSettings;
import midien.kheldiente.cma.utils.CropLocationHelper;
import midien.kheldiente.cma.utils.scheduler.SchedulerProvider;
import midien.kheldiente.cma.utils.weather.OpenWeather;

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

        presenter = new TakeCropPhotoPresenter(takeCropPhotoFragment,
                AppSettings.getInstance(this),
                CropLocationHelper.getInstance(this),
                OpenWeather.getInstance(),
                SchedulerProvider.getInstance());
    }
}
