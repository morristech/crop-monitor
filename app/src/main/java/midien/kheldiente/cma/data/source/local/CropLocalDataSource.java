package midien.kheldiente.cma.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import midien.kheldiente.cma.data.Crop;
import midien.kheldiente.cma.data.source.CropDataSource;
import midien.kheldiente.cma.data.Image;
import midien.kheldiente.cma.utils.scheduler.BaseSchedulerProvider;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.QueryObservable;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

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
        long id = c.getLong(c.getColumnIndexOrThrow(CelpaPersistenceContract.CropEntry._ID));
        String name = c.getString(c.getColumnIndexOrThrow(CelpaPersistenceContract.CropEntry.COL_CROP_NAME));
        String imgPath = c.getString(c.getColumnIndexOrThrow(CelpaPersistenceContract.CropEntry.COL_CROP_IMG_PATH));
        String noOfFertsUsed = c.getString(c.getColumnIndexOrThrow(CelpaPersistenceContract.CropEntry.COL_NO_OF_FERTS_USED));
        String noOfWaterApplied = c.getString(c.getColumnIndexOrThrow(CelpaPersistenceContract.CropEntry.COL_NO_OF_WATER_APPLIED));
        long plantedStartDate = c.getLong(c.getColumnIndexOrThrow(CelpaPersistenceContract.CropEntry.COL_PLANTED_START_DATE));
        String weather = c.getString(c.getColumnIndexOrThrow(CelpaPersistenceContract.CropEntry.COL_WEATHER));

        Crop crop = new Crop();
        crop.id = id;
        crop.name = name;
        crop.img.add(new Image(imgPath));
        crop.noOfFertilizersUsed = noOfFertsUsed;
        crop.noOfWaterAppliedPerDay = noOfWaterApplied;
        crop.plantedStartDate = plantedStartDate;
        crop.weather = weather;

        return crop;
    }

    @Override
    public Flowable<List<Crop>> getCrops() {
        String[] projection = {
                CelpaPersistenceContract.CropEntry._ID,
                CelpaPersistenceContract.CropEntry.COL_CROP_NAME,
                CelpaPersistenceContract.CropEntry.COL_CROP_IMG_PATH,
                CelpaPersistenceContract.CropEntry.COL_NO_OF_FERTS_USED,
                CelpaPersistenceContract.CropEntry.COL_NO_OF_WATER_APPLIED,
                CelpaPersistenceContract.CropEntry.COL_PLANTED_START_DATE,
                CelpaPersistenceContract.CropEntry.COL_WEATHER
        };

        String sql = String.format("SELECT %s FROM %s",
                TextUtils.join(",", projection), CelpaPersistenceContract.CropEntry._ID);

        return databaseHelper.createQuery(CelpaPersistenceContract.CropEntry.TB_CROP, sql)
                .mapToList(cropMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<JsonArray> getCropsInJson(long farmerId) {
        return null;
    }

    @Override
    public Flowable<Crop> getCrop(String id) {
        String[] projection = {
                CelpaPersistenceContract.CropEntry._ID,
                CelpaPersistenceContract.CropEntry.COL_CROP_NAME,
                CelpaPersistenceContract.CropEntry.COL_CROP_IMG_PATH,
                CelpaPersistenceContract.CropEntry.COL_NO_OF_FERTS_USED,
                CelpaPersistenceContract.CropEntry.COL_NO_OF_WATER_APPLIED,
                CelpaPersistenceContract.CropEntry.COL_PLANTED_START_DATE,
                CelpaPersistenceContract.CropEntry.COL_WEATHER
        };

        String sql = String.format("SELECT %s FROM %s WHERE %s = ?",
                TextUtils.join(",", projection), CelpaPersistenceContract.CropEntry._ID);

        return databaseHelper.createQuery(CelpaPersistenceContract.CropEntry.TB_CROP, sql, id)
                .mapToOneOrDefault(cursor ->
                    cropMapperFunction.apply(cursor), new Crop())
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<JsonObject> saveCrop(Crop crop) {

        ContentValues values = new ContentValues();
        values.put(CelpaPersistenceContract.CropEntry.COL_CROP_NAME, crop.name);
        // For now get first index in list of imagePaths
        values.put(CelpaPersistenceContract.CropEntry.COL_CROP_IMG_PATH, crop.img.get(0).imgPath);
        values.put(CelpaPersistenceContract.CropEntry.COL_NO_OF_FERTS_USED, crop.noOfFertilizersUsed);
        values.put(CelpaPersistenceContract.CropEntry.COL_NO_OF_WATER_APPLIED, crop.noOfWaterAppliedPerDay);
        values.put(CelpaPersistenceContract.CropEntry.COL_WEATHER, crop.weather);

        databaseHelper.insert(CelpaPersistenceContract.CropEntry.TB_CROP, values);

        return QueryObservable.just(crop.toJsonObject()).toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable deleteAll() {
        databaseHelper.delete(CelpaPersistenceContract.CropEntry.TB_CROP, null, new String[]{});
        return QueryObservable.just(Flowable.empty()).toFlowable(BackpressureStrategy.BUFFER);
    }

}
