package midien.kheldiente.cma.data.source;


import midien.kheldiente.cma.data.Farmer;
import com.google.gson.JsonObject;

import io.reactivex.Flowable;

public interface FarmerDataSource {

    Flowable<JsonObject> registerFarmer(Farmer farmer);

    Flowable<JsonObject> getFarmer(String userName, String password);

    Flowable<JsonObject> getFarmer(String id);

    Flowable<Farmer> saveFarmer(Farmer farmer);

    Flowable deleteAll();

}
