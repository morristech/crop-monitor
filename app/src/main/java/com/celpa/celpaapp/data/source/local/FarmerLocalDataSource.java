package com.celpa.celpaapp.data.source.local;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.celpa.celpaapp.data.Farmer;
import com.celpa.celpaapp.data.source.FarmerDataSource;
import com.celpa.celpaapp.utils.scheduler.BaseSchedulerProvider;
import com.google.gson.JsonObject;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.QueryObservable;
import com.squareup.sqlbrite2.SqlBrite;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

import com.celpa.celpaapp.data.source.local.CelpaPersistenceContract.*;

import static com.celpa.celpaapp.data.source.local.CelpaPersistenceContract.FarmerEntry.TB_FARMER;

public class FarmerLocalDataSource implements FarmerDataSource {

    private static FarmerLocalDataSource INSTANCE;

    private final BriteDatabase databaseHelper;

    private Function<Cursor, JsonObject> farmerMapperFunction;

    private Context context;

    public static FarmerLocalDataSource getInstance(Context context, BaseSchedulerProvider schedulerProvider) {
        if(INSTANCE == null) {
            INSTANCE = new FarmerLocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }

    private FarmerLocalDataSource(Context context, BaseSchedulerProvider schedulerProvider) {
        this.context = context;
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        CelpaDbHelper dbHelper = CelpaDbHelper.getInstance(context);
        databaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, schedulerProvider.io());
        farmerMapperFunction = this::getFarmer;
    }

    private JsonObject getFarmer(Cursor c) {
        long id = c.getLong(c.getColumnIndexOrThrow(FarmerEntry._ID));
        String firstName = c.getString(c.getColumnIndexOrThrow(FarmerEntry.COL_FIRST_NAME));
        String lastName = c.getString(c.getColumnIndexOrThrow(FarmerEntry.COL_LAST_NAME));
        String email = c.getString(c.getColumnIndexOrThrow(FarmerEntry.COL_EMAIL));
        String userName = c.getString(c.getColumnIndexOrThrow(FarmerEntry.COL_USER_NAME));
        String password = c.getString(c.getColumnIndexOrThrow(FarmerEntry.COL_PASSWORD));

        Farmer farmer = new Farmer();
        farmer.id = id;
        farmer.firstName = firstName;
        farmer.lastName = lastName;
        farmer.email = email;
        farmer.userName = userName;
        farmer.password = password;

        return farmer.toJsonObject();
    }

    @Override
    public Flowable<JsonObject> registerFarmer(Farmer farmer) {
        return QueryObservable.just(new JsonObject()).toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<JsonObject> getFarmer(String userName, String password) {
        String[] projection = {
                FarmerEntry._ID,
                FarmerEntry.COL_FIRST_NAME,
                FarmerEntry.COL_LAST_NAME,
                FarmerEntry.COL_USER_NAME,
                FarmerEntry.COL_EMAIL,
                FarmerEntry.COL_USER_NAME,
                FarmerEntry.COL_PASSWORD
        };

        String sql = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ?",
                TextUtils.join(",", projection), TB_FARMER, FarmerEntry._ID);

        return databaseHelper.createQuery(TB_FARMER, sql, userName, password)
                .mapToOneOrDefault(cursor -> farmerMapperFunction.apply(cursor), new JsonObject())
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<JsonObject> getFarmer(String id) {
        String[] projection = {
                FarmerEntry._ID,
                FarmerEntry.COL_FIRST_NAME,
                FarmerEntry.COL_LAST_NAME,
                FarmerEntry.COL_USER_NAME,
                FarmerEntry.COL_EMAIL,
                FarmerEntry.COL_USER_NAME,
                FarmerEntry.COL_PASSWORD
        };

        String sql = String.format("SELECT %s FROM %s WHERE %S = ?",
                TextUtils.join(",", projection), TB_FARMER, FarmerEntry._ID);

        return databaseHelper.createQuery(TB_FARMER, sql, id)
                .mapToOneOrDefault(cursor -> farmerMapperFunction.apply(cursor), new JsonObject())
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<Farmer> saveFarmer(Farmer farmer) {
        ContentValues values = new ContentValues();
        values.put(FarmerEntry.COL_FIRST_NAME, farmer.firstName);
        values.put(FarmerEntry.COL_LAST_NAME, farmer.lastName);
        values.put(FarmerEntry.COL_EMAIL, farmer.email);
        values.put(FarmerEntry.COL_USER_NAME, farmer.userName);
        values.put(FarmerEntry.COL_PASSWORD, farmer.password);

        databaseHelper.insert(TB_FARMER, values);

        return QueryObservable.just(new Farmer()).toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable deleteAll() {
        databaseHelper.delete(TB_FARMER, null, new String[]{});
        return QueryObservable.just(Flowable.empty()).toFlowable(BackpressureStrategy.BUFFER);
    }
}
