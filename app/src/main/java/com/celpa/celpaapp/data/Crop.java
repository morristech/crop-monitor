package com.celpa.celpaapp.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Crop implements Parcelable {

    public long id;
    public List<Image> img = new ArrayList<>(0);
    public String name;
    public long noOfFertilizersUsed;
    public long approxDateOfHarvest;
    public long noOfWaterAppliedPerDay;
    public String weather;

    public Crop() {}

    public Crop(Parcel in) {
        id = in.readLong();
        in.readList(img, Image.class.getClassLoader());
        name = in.readString();
        noOfFertilizersUsed = in.readLong();
        approxDateOfHarvest = in.readLong();
        noOfWaterAppliedPerDay = in.readLong();
        weather = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeList(img);
        dest.writeString(name);
        dest.writeLong(noOfFertilizersUsed);
        dest.writeLong(approxDateOfHarvest);
        dest.writeLong(noOfWaterAppliedPerDay);
        dest.writeString(weather);
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
}
