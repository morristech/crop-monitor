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

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void setFarmer(Context context, int id) {
        SharedPreferences prefs = getPrefs(context);
        prefs.edit().putInt(PREF_FARMER_ID, id).apply();
    }

    public static int getFarmerLoggedIn(Context context) {
        SharedPreferences prefs = getPrefs(context);
        return prefs.getInt(PREF_FARMER_ID, 0);
    }

    public static Flowable clearAll(Context context) {
        SharedPreferences prefs = getPrefs(context);
        prefs.edit().clear();
        prefs.edit().commit();

        return deleteAllRowsInDatabase(context);
    }

    private static Flowable deleteAllRowsInDatabase(Context context) {

        FarmerLocalDataSource farmerLocalDataSource = FarmerLocalDataSource.getInstance(context, SchedulerProvider.getInstance());
        CropLocalDataSource cropLocalDataSource = CropLocalDataSource.getInstance(context, SchedulerProvider.getInstance());

        // Combine flowables
        Flowable flowables = Flowable.concat(farmerLocalDataSource.deleteAll(), cropLocalDataSource.deleteAll())
                .firstOrError()
                .toFlowable();

        return flowables;
    }

}
