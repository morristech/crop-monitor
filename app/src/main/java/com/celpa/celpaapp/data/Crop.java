package com.celpa.celpaapp.data;

import android.graphics.Bitmap;
import android.location.Address;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Crop implements Parcelable {

    public long id = 0;
    public long farmerId = 0;
    public List<Image> img = new ArrayList<>(0);
    public String name = "";
    public long timeStamp = 0;
    public double noOfFertilizersUsed = 0;
    public long plantedStartDate = 0;
    public double noOfWaterAppliedPerDay = 0;
    public String weather = "";
    public String location = ""; // A json string

    public Crop() {}

    public Crop(Parcel in) {
        id = in.readLong();
        farmerId = in.readLong();
        name = in.readString();
        in.readList(img, Image.class.getClassLoader());
        timeStamp = in.readLong();
        noOfFertilizersUsed = in.readLong();
        plantedStartDate = in.readLong();
        noOfWaterAppliedPerDay = in.readLong();
        weather = in.readString();
        location = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(farmerId);
        dest.writeString(name);
        dest.writeList(img);
        dest.writeLong(timeStamp);
        dest.writeDouble(noOfFertilizersUsed);
        dest.writeLong(plantedStartDate);
        dest.writeDouble(noOfWaterAppliedPerDay);
        dest.writeString(weather);
        dest.writeString(location);
    }

    public static final Creator<Crop> CREATOR = new Creator<Crop>() {

        @Override
        public Crop createFromParcel(Parcel source) {
            return new Crop(source);
        }

        @Override
        public Crop[] newArray(int size) {
            return new Crop[0];
        }
    };

    @Override
    public String toString() {
        return String.format("id: %s, name: %s, img: %s, timeStamp: %s, " +
                "noOfFertilizersUsed: %s, plantedStartDate: %s, noOfWaterAppliedPerDay: %s" +
                "weather: %s, location: %s",
                id, name, img,
                timeStamp, noOfFertilizersUsed, plantedStartDate,
                noOfWaterAppliedPerDay, weather, location);
    }

    public JsonObject toJsonObject() {
        JsonObject json = new JsonObject();
        json.addProperty("farmer_id", farmerId);
        json.addProperty("id", id);
        json.addProperty("name", name);

        JsonArray photoArr = new JsonArray();
        for(Image img: img) {
            photoArr.add(img.imgPath);
        }
        json.add("img_path", photoArr);
        json.addProperty("timeStamp", timeStamp);
        json.addProperty("no_of_ferts_used", noOfFertilizersUsed);
        json.addProperty("no_of_water_applied", noOfWaterAppliedPerDay);
        json.addProperty("planted_start_date", plantedStartDate);
        json.addProperty("weather", weather);
        json.addProperty("location", location);

        return json;
    }

    public static Crop getCrop(JsonObject obj) {
        Crop crop = new Crop();
        crop.id = obj.getAsJsonPrimitive("id").getAsLong();
        crop.name = obj.getAsJsonPrimitive("name").getAsString();
        crop.location = obj.getAsJsonObject("location").toString();
        crop.noOfFertilizersUsed = obj.getAsJsonPrimitive("no_of_ferts_used").getAsDouble();
        crop.noOfWaterAppliedPerDay = obj.getAsJsonPrimitive("no_of_water_applied").getAsDouble();
        crop.plantedStartDate = obj.getAsJsonPrimitive("planted_start_date").getAsLong();
        crop.weather = obj.getAsJsonPrimitive("weather").getAsString();
        crop.timeStamp = obj.getAsJsonPrimitive("timestamp").getAsLong();

        JsonObject imgPath = obj.getAsJsonObject("img_path");
        for(JsonElement imgElement: imgPath.getAsJsonArray("photos")) {
            crop.img.add(new Image(imgElement.getAsString()));
        }
        return crop;
    }

    public static JsonObject convertLocationToJsonObject(Address address) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("address", address.getAddressLine(0));
        jsonObject.addProperty("latitude", address.getLatitude());
        jsonObject.addProperty("longitude", address.getLongitude());

        return jsonObject;
    }
}
