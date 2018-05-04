package midien.kheldiente.cma.data.source;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Flowable;
import midien.kheldiente.cma.data.Crop;

public interface CropDataSource {

    Flowable<List<Crop>> getCrops();

    Flowable<JsonArray> getCropsInJson(long farmerId);

    Flowable<Crop> getCrop(String id);

    Flowable<JsonObject> saveCrop(Crop crop);

    Flowable deleteAll();
}
