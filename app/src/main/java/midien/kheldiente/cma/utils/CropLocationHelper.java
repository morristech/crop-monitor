package midien.kheldiente.cma.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class CropLocationHelper {

    private static final int LOCATION_INTERVAL = 1000;
    private static final int LOCATION_PRIORITY = LocationRequest.PRIORITY_HIGH_ACCURACY;

    private static CropLocationHelper INSTANCE;

    private RxLocation rxLocation;
    private LocationRequest locationRequest;

    public static CropLocationHelper getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new CropLocationHelper(context);
        }
        return INSTANCE;
    }

    private CropLocationHelper(Context context) {
        rxLocation = new RxLocation(context);
        init();
    }

    private void init() {
        locationRequest = LocationRequest.create()
                .setPriority(LOCATION_PRIORITY)
                .setInterval(LOCATION_INTERVAL);
    }

    @SuppressLint("MissingPermission")
    public Flowable<Location> getLastLocation() {
        return rxLocation.location().updates(locationRequest, BackpressureStrategy.BUFFER);
    }

    public RxLocation getRxLocation() {
        return rxLocation;
    }
}
