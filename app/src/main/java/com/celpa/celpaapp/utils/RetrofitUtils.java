package com.celpa.celpaapp.utils;


import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RetrofitUtils {

    public static RequestBody createPartFromJson(JsonObject json) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), json.toString());
    }

}
