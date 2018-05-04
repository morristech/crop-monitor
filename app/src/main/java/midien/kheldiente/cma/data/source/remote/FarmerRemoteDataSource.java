package midien.kheldiente.cma.data.source.remote;


import midien.kheldiente.cma.data.Farmer;
import midien.kheldiente.cma.data.source.FarmerDataSource;
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
    public Flowable<JsonObject> registerFarmer(Farmer farmer) {
        JsonObject json = new JsonObject();
        json.addProperty("firstName", farmer.firstName);
        json.addProperty("lastName", farmer.lastName);
        json.addProperty("email", farmer.email);
        json.addProperty("mobile_number", farmer.mobileNumber);
        json.addProperty("userName", farmer.userName);
        json.addProperty("password", farmer.password);

        CelpaApiService apiService = CelpaApiHelper.getApiInstance();
        return apiService.registerFarmer(json);
    }


    @Override
    public Flowable<JsonObject> getFarmer(String userName, String password) {
        CelpaApiService celpaApiService = CelpaApiHelper.getApiInstance();
        return celpaApiService.getFarmer(userName, password)
                .singleElement()
                .toFlowable();
    }

    @Override
    public Flowable<JsonObject> getFarmer(String id) {
        return null;
    }

    @Override
    public Flowable<Farmer> saveFarmer(Farmer farmer) {
        return null;
    }

    @Override
    public Flowable deleteAll() {
        return null;
    }


}
