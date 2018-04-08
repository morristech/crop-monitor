package com.celpa.celpaapp.data.source.remote;


import com.celpa.celpaapp.data.Farmer;
import com.celpa.celpaapp.data.source.FarmerDataSource;
import com.google.gson.JsonObject;


import io.reactivex.Flowable;

public class FarmerRemoteDataSource implements FarmerDataSource {

    private static FarmerRemoteDataSource INSTANCE;

    public static FarmerRemoteDataSource getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new FarmerRemoteDataSource();
        }

        return INSTANCE;
    }

    @Override
    public void registerFarmer(Farmer farmer) {}

    @Override
    public Flowable<JsonObject> loginFarmer(String userName, String password) {
        CelpaApiService celpaApiService = CelpaApiHelper.getApiInstance();
        return celpaApiService.isFarmerRegistered(userName, password)
                .singleElement()
                .toFlowable();
    }

}
