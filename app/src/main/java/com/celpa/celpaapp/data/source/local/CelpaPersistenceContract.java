package com.celpa.celpaapp.data.source.local;


import android.provider.BaseColumns;

public class CelpaPersistenceContract {

    private CelpaPersistenceContract() {}

    public static abstract class CropEntry implements BaseColumns {

        private static final String TEXT_TYPE = " TEXT";
        private static final String INTEGER_TYPE = " INTEGER";
        private static final String COMMA_SEP = ",";

        public static final String TB_CROP = "crop";
        public static final String COL_CROP_NAME = "crop_name";
        public static final String COL_CROP_IMG_PATH = "img";
        public static final String COL_NO_OF_FERTS_USED = "no_of_ferts_used";
        public static final String COL_NO_OF_WATER_APPLIED = "no_of_water_applied";
        public static final String COL_APPROX_DATE_HARVEST = "approx_date_harvest";
        public static final String COL_WEATHER = "waether";

        public static final String CREATE_TB_CROP = "CREATE TABLE " + TB_CROP + "(" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                COL_CROP_NAME + TEXT_TYPE + COMMA_SEP +
                COL_CROP_IMG_PATH + TEXT_TYPE + COMMA_SEP +
                COL_WEATHER + TEXT_TYPE + COMMA_SEP +
                COL_NO_OF_FERTS_USED + INTEGER_TYPE + COMMA_SEP +
                COL_NO_OF_WATER_APPLIED + INTEGER_TYPE + COMMA_SEP +
                COL_APPROX_DATE_HARVEST + INTEGER_TYPE + ")";

        public static final String DROP_TB_CROP = "DROP TABLE IF EXISTS " + TB_CROP;

    }
}
