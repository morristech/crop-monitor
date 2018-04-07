package com.celpa.celpaapp.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.celpa.celpaapp.data.source.local.CelpaPersistenceContract.CropEntry.CREATE_TB_CROP;
import static com.celpa.celpaapp.data.source.local.CelpaPersistenceContract.CropEntry.DROP_TB_CROP;

public class CelpaDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "celpa.db";

    private Context context;
    private static CelpaDbHelper INSTANCE;

    public static CelpaDbHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CelpaDbHelper(context);
        }

        return INSTANCE;
    }

    public CelpaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TB_CROP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TB_CROP);
    }
}
