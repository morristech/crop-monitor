package com.celpa.celpaapp.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.CropDataSource;
import com.celpa.celpaapp.data.Image;
import com.celpa.celpaapp.utils.BitmapUtils;
import com.celpa.celpaapp.utils.scheduler.BaseSchedulerProvider;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.QueryObservable;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.List;
import java.util.Optional;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

import com.celpa.celpaapp.data.source.local.CelpaPersistenceContract.*;

import static com.celpa.celpaapp.data.source.local.CelpaPersistenceContract.CropEntry.TB_CROP;

public class CropLocalDataSource implements CropDataSource {

    private static CropLocalDataSource INSTANCE;

    private final BriteDatabase databaseHelper;

    private Function<Cursor, Crop> cropMapperFunction;

    private Context context;

    public static CropLocalDataSource getInstance(Context context, BaseSchedulerProvider schedulerProvider) {
        if(INSTANCE == null) {
            INSTANCE = new CropLocalDataSource(context, schedulerProvider);
        }

        return INSTANCE;
    }

    private CropLocalDataSource(Context context, BaseSchedulerProvider schedulerProvider) {
        this.context = context;
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        CelpaDbHelper dbHelper = CelpaDbHelper.getInstance(context);
        databaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, schedulerProvider.io());
        cropMapperFunction = this::getCrop;
    }

    private Crop getCrop(Cursor c) {
        long id = c.getLong(c.getColumnIndexOrThrow(CropEntry._ID));
        String name = c.getString(c.getColumnIndexOrThrow(CropEntry.COL_CROP_NAME));
        String imgPath = c.getString(c.getColumnIndexOrThrow(CropEntry.COL_CROP_IMG_PATH));
        long noOfFertsUsed = c.getLong(c.getColumnIndexOrThrow(CropEntry.COL_NO_OF_FERTS_USED));
        long noOfWaterApplied = c.getLong(c.getColumnIndexOrThrow(CropEntry.COL_NO_OF_WATER_APPLIED));
        long approxDateHarvest = c.getLong(c.getColumnIndexOrThrow(CropEntry.COL_APPROX_DATE_HARVEST));
        String weather = c.getString(c.getColumnIndexOrThrow(CropEntry.COL_WEATHER));

        Crop crop = new Crop();
        crop.id = id;
        crop.name = name;
        crop.img.add(new Image(imgPath));
        crop.noOfFertilizersUsed = noOfFertsUsed;
        crop.noOfWaterAppliedPerDay = noOfWaterApplied;
        crop.approxDateOfHarvest = approxDateHarvest;
        crop.weather = weather;

        return crop;
    }

    @Override
    public Flowable<List<Crop>> getCrops() {
        String[] projection = {
                CropEntry._ID,
                CropEntry.COL_CROP_NAME,
                CropEntry.COL_CROP_IMG_PATH,
                CropEntry.COL_NO_OF_FERTS_USED,
                CropEntry.COL_NO_OF_WATER_APPLIED,
                CropEntry.COL_APPROX_DATE_HARVEST,
                CropEntry.COL_WEATHER
        };

        String sql = String.format("SELECT %s FROM %s",
                TextUtils.join(",", projection), CropEntry._ID);

        return databaseHelper.createQuery(TB_CROP, sql)
                .mapToList(cropMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<JsonArray> getCropsInJson() {
        return null;
    }

    @Override
    public Flowable<Crop> getCrop(String id) {
        String[] projection = {
                CropEntry._ID,
                CropEntry.COL_CROP_NAME,
                CropEntry.COL_CROP_IMG_PATH,
                CropEntry.COL_NO_OF_FERTS_USED,
                CropEntry.COL_NO_OF_WATER_APPLIED,
                CropEntry.COL_APPROX_DATE_HARVEST,
                CropEntry.COL_WEATHER
        };

        String sql = String.format("SELECT %s FROM %s WHERE %s = ?",
                TextUtils.join(",", projection), CropEntry._ID);

        return databaseHelper.createQuery(TB_CROP, sql, id)
                .mapToOneOrDefault(cursor ->
                    cropMapperFunction.apply(cursor), new Crop())
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<JsonObject> saveCrop(Crop crop) {

        ContentValues values = new ContentValues();
        values.put(CropEntry.COL_CROP_NAME, crop.name);
        // For now get first index in list of imagePaths
        values.put(CropEntry.COL_CROP_IMG_PATH, crop.img.get(0).imgPath);
        values.put(CropEntry.COL_NO_OF_FERTS_USED, crop.noOfFertilizersUsed);
        values.put(CropEntry.COL_NO_OF_WATER_APPLIED, crop.noOfWaterAppliedPerDay);
        values.put(CropEntry.COL_WEATHER, crop.weather);

        databaseHelper.insert(TB_CROP, values);

        return QueryObservable.just(crop.toJsonObject()).toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable deleteAll() {
        databaseHelper.delete(TB_CROP, null, new String[]{});
        return QueryObservable.just(Flowable.empty()).toFlowable(BackpressureStrategy.BUFFER);
    }

}
