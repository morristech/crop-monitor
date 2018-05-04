package midien.kheldiente.cma.data.source.remote;


import android.content.Context;
import android.net.Uri;

import midien.kheldiente.cma.data.Crop;
import midien.kheldiente.cma.data.source.CropDataSource;
import midien.kheldiente.cma.data.Image;
import midien.kheldiente.cma.utils.PhotoUploadHelper;
import midien.kheldiente.cma.utils.RetrofitUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CropRemoteDataSource implements CropDataSource {

    private static CropRemoteDataSource INSTANCE;

    private Context context;

    public static CropRemoteDataSource getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new CropRemoteDataSource(context);
        }

        return INSTANCE;
    }

    private CropRemoteDataSource(Context context) {
        this.context = context;
    }

    @Override
    public Flowable<List<Crop>> getCrops() {
        return null;
    }

    @Override
    public Flowable<JsonArray> getCropsInJson(long farmerId) {
        CelpaApiService apiService = CelpaApiHelper.getApiInstance();
        return apiService.getCrops(farmerId);
    }

    @Override
    public Flowable<Crop> getCrop(String id) {
        return null;
    }

    @Override
    public Flowable<JsonObject> saveCrop(Crop crop) {
        List<MultipartBody.Part> files = new ArrayList<>(0);

        for(Image photo: crop.img) {
            files.add(PhotoUploadHelper.prepareFilePart(context,
                    "photos",
                    Uri.fromFile(new File(photo.imgPath))));
        }

        JsonObject json = new JsonObject();
        json.addProperty("farmer_id", crop.farmerId);
        json.addProperty("name", crop.name);
        json.addProperty("no_of_ferts_used", crop.noOfFertilizersUsed);
        json.addProperty("no_of_water_applied", crop.noOfWaterAppliedPerDay);
        json.addProperty("planted_start_date", crop.plantedStartDate);
        json.addProperty("weather", crop.weather);
        json.addProperty("planted_duration", crop.plantedDuration);

        try {
            JsonObject locObj = new JsonParser().parse(crop.location).getAsJsonObject();
            json.add("location", locObj);
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        json.addProperty("timeStamp", crop.timeStamp);
        json.addProperty("quantity", crop.quantity);
        json.addProperty("square_meter", crop.squareMeter);
        json.addProperty("post_to_market", crop.postToMarket);

        RequestBody requestBody = RetrofitUtils.createPartFromJson(json);

        // Save to remote server
        CelpaApiService apiService = CelpaApiHelper.getApiInstance();
        return apiService.uploadCropDetails(files, requestBody);
    }

    @Override
    public Flowable deleteAll() {
        return null;
    }
}
