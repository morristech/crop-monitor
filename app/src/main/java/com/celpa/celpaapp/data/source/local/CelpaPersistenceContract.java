package com.celpa.celpaapp.data.source.local;


import android.provider.BaseColumns;

public class CelpaPersistenceContract {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String DECIMAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String PRIMARY_KEY = " PRIMARY KEY";

    private CelpaPersistenceContract() {}

    public static abstract class CropEntry implements BaseColumns {

        public static final String TB_CROP = "crop";
        public static final String COL_CROP_NAME = "crop_name";
        public static final String COL_CROP_IMG_PATH = "imgPath"; // {photos: [url, url]}
        public static final String COL_TIMESTAMP = "timeStamp";
        public static final String COL_NO_OF_FERTS_USED = "no_of_ferts_used";
        public static final String COL_NO_OF_WATER_APPLIED = "no_of_water_applied";
        public static final String COL_PLANTED_START_DATE = "planted_start_date";
        public static final String COL_LOCATION = "location"; // {address: "", lat: "", lng: ""}
        public static final String COL_WEATHER = "weather";

        public static final String CREATE_TB_CROP = "CREATE TABLE " + TB_CROP + "(" +
                _ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                COL_CROP_NAME + TEXT_TYPE + COMMA_SEP +
                COL_TIMESTAMP + INTEGER_TYPE + COMMA_SEP +
                COL_CROP_IMG_PATH + TEXT_TYPE + COMMA_SEP +
                COL_LOCATION + TEXT_TYPE + COMMA_SEP +
                COL_WEATHER + TEXT_TYPE + COMMA_SEP +
                COL_NO_OF_FERTS_USED + DECIMAL_TYPE + COMMA_SEP +
                COL_NO_OF_WATER_APPLIED + DECIMAL_TYPE + COMMA_SEP +
                COL_PLANTED_START_DATE + INTEGER_TYPE + ")";

        public static final String DROP_TB_CROP = "DROP TABLE IF EXISTS " + TB_CROP;

    }

    public static abstract class FarmerEntry implements BaseColumns {

        public static final String TB_FARMER = "farmer";
        public static final String COL_FIRST_NAME = "firstName";
        public static final String COL_LAST_NAME = "lastName";
        public static final String COL_EMAIL = "email";
        public static final String COL_MOBILE_NUMBER = "mobile_number";
        public static final String COL_USER_NAME = "userName";
        public static final String COL_PASSWORD = "password";
        public static final String COL_FARMER_ID = "farmerId";

        public static final String CREATE_TB_FARMER = "CREATE TABLE " + TB_FARMER + "(" +
                _ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                COL_FARMER_ID + INTEGER_TYPE + COMMA_SEP +
                COL_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                COL_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                COL_MOBILE_NUMBER + INTEGER_TYPE + COMMA_SEP +
                COL_EMAIL + TEXT_TYPE + COMMA_SEP +
                COL_USER_NAME + TEXT_TYPE + COMMA_SEP +
                COL_PASSWORD + TEXT_TYPE + ")";

        public static final String DROP_TB_FARMER = "DROP TABLE IF EXISTS " + TB_FARMER;

    }
}
