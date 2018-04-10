package com.celpa.celpaapp.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.celpa.celpaapp.data.source.local.CropLocalDataSource;
import com.celpa.celpaapp.data.source.local.FarmerLocalDataSource;
import com.celpa.celpaapp.utils.scheduler.SchedulerProvider;

import io.reactivex.Flowable;

public class AppSettings {

    private static final String TAG = AppSettings.class.getSimpleName();

    private static final String PREF_NAME = "settings_pref";
    private static final String PREF_FARMER_ID = "farmer_id";

    private static AppSettings INSTANCE;

    private Context context;
    private SharedPreferences prefs;

    public static AppSettings getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new AppSettings(context);
        }
        return INSTANCE;
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    private AppSettings(Context context) {
        this.context = context;
        prefs = getPrefs(context);
    }

    public void setFarmer(long id) {
        prefs.edit().putLong(PREF_FARMER_ID, id).apply();
    }

    public long getFarmerLoggedIn() {
        return prefs.getLong(PREF_FARMER_ID, 0);
    }

    public boolean isLoggedIn() {
        Log.d(TAG, String.valueOf(prefs.getLong(PREF_FARMER_ID, 0)));
        return prefs.getLong(PREF_FARMER_ID, 0) > 0;
    }

    public Flowable clearAll() {
        prefs.edit().clear().apply();
        return deleteAllRowsInDatabase(context);
    }

    private Flowable deleteAllRowsInDatabase(Context context) {

        FarmerLocalDataSource farmerLocalDataSource = FarmerLocalDataSource.getInstance(context, SchedulerProvider.getInstance());
        CropLocalDataSource cropLocalDataSource = CropLocalDataSource.getInstance(context, SchedulerProvider.getInstance());

        // Combine flowables
        Flowable flowables = Flowable.concat(farmerLocalDataSource.deleteAll(), cropLocalDataSource.deleteAll())
                .firstOrError()
                .toFlowable();

        return flowables;
    }

}
