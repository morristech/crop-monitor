package midien.kheldiente.cma;

import android.util.Log;

import midien.kheldiente.cma.utils.scheduler.BaseSchedulerProvider;
import midien.kheldiente.cma.utils.scheduler.SchedulerProvider;
import midien.kheldiente.cma.utils.weather.OpenWeather;
import midien.kheldiente.cma.utils.weather.OpenWeatherApiService;
import midien.kheldiente.cma.utils.weather.OpenWeatherHelper;
import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class OpenWeatherTest {

    private static final String TAG = OpenWeatherTest.class.getSimpleName();

    private OpenWeatherApiService apiService;
    private CompositeDisposable compositeDisposable;
    private BaseSchedulerProvider schedulerProvider;

    @Before
    public void init() {
        apiService = OpenWeatherHelper.getApiInstance();
        compositeDisposable = new CompositeDisposable();
        schedulerProvider = SchedulerProvider.getInstance();
    }

    @Test
    public void getWeatherFromCoords() {
        Flowable<JsonObject> getWeather = apiService.getWeatherFromCoords(35, 139, BuildConfig.OPEN_WEATHER_API_KEY);
        compositeDisposable.clear();

        Disposable disposable = getWeather
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.io())
                .subscribe(jsonObject -> {
                    String weather = OpenWeather.getWeather(jsonObject);
                    Log.d(TAG, weather);
                });

        compositeDisposable.add(disposable);
    }

}
