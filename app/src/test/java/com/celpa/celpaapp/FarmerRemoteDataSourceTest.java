package com.celpa.celpaapp;

import android.util.Log;

import com.celpa.celpaapp.data.source.remote.CelpaApiHelper;
import com.celpa.celpaapp.data.source.remote.CelpaApiService;
import com.celpa.celpaapp.data.source.remote.CropRemoteDataSource;
import com.celpa.celpaapp.data.source.remote.FarmerRemoteDataSource;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Flowable;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FarmerRemoteDataSourceTest {

    private static final String TAG = FarmerRemoteDataSourceTest.class.getSimpleName();

    public FarmerRemoteDataSource farmerRemoteDataSource;

    @Before
    public void init() {
        farmerRemoteDataSource = new FarmerRemoteDataSource();
    }

    @Test
    public void getFarmers() {
        farmerRemoteDataSource.getFarmers()
                .flatMap(farmers -> Flowable.fromIterable(farmers)
                        .doOnNext(farmer -> {
                            Log.d(TAG, farmer.toString());
                }));
    }

    @Test
    public void doesFarmerExists() {
        CelpaApiService celpaApiService = CelpaApiHelper.getApiInstance();
        celpaApiService.isFarmerRegistered("kheldiente", "mikediente123")
                .doOnNext(json -> {
                    boolean success = json.getAsJsonObject("success").getAsBoolean();
                    Log.d(TAG, String.valueOf(success));
                });
    }
}