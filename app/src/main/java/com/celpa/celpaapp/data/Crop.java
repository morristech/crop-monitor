package com.celpa.celpaapp.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Crop implements Parcelable {

    public byte[] img;
    public String name;
    public long noOfFertilizersUsed;
    public long approxDateOfHarvest;
    public long noOfWaterAppliedPerDay;
    public String weather;

    public Crop() {}

    public Crop(Parcel in) {
        img = in.createByteArray();
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
        dest.writeByteArray(img);
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
