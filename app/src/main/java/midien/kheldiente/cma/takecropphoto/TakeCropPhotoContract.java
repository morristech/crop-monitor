package midien.kheldiente.cma.takecropphoto;


import android.location.Location;

import midien.kheldiente.cma.BasePresenter;
import midien.kheldiente.cma.BaseView;
import midien.kheldiente.cma.data.Crop;
import com.google.gson.JsonObject;

import io.reactivex.Flowable;

public interface TakeCropPhotoContract {

    interface View extends BaseView<Presenter> {

        boolean takePhoto();

        void goToAddCropDetails();

        void goToAddCropDetails(Crop crop);

        String saveBitmapToStorage(byte[] photo);

        void showLoadingDialog(String msg);

        void updateLoadingDialog(String msg);

        void hideLoadingDialog();

        void showOkDialog(String msg);

        String getGettingLocationText();

        String getGettingWeatherText();

        String getParsingImageText();

        String getLoggingOutText();

        void closeMe();

        void goToLogin();

        void goToList();
    }

    interface Presenter extends BasePresenter {

        Flowable<Location> getLocation();

        Flowable<JsonObject> getWeather(double lat, double lon);

        void processPhoto(byte[] photo);

        void clearCacheAndLogout();
    }

}
