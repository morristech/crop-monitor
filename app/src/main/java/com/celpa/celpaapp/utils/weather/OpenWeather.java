package com.celpa.celpaapp.utils.weather;


import com.celpa.celpaapp.BuildConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.reactivex.Flowable;

public class OpenWeather {

    private static OpenWeather INSTANCE;
    private OpenWeatherApiService apiService;

    public static OpenWeather getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new OpenWeather();
        }
        return INSTANCE;
    }

    public OpenWeather() {
        apiService = OpenWeatherHelper.getApiInstance();
    }

    public Flowable<JsonObject> getWeatherFromCoords(double latitude, double longitude) {
        return apiService.getWeatherFromCoords(latitude, longitude, BuildConfig.OPEN_WEATHER_API_KEY);
    }

    public static String getWeather(JsonObject jsonObject) {
        String description = "--";
        JsonArray weatherArr = jsonObject.getAsJsonArray("weather");
        for(JsonElement element: weatherArr) {
            JsonObject obj = element.getAsJsonObject();
            description = obj.getAsJsonPrimitive("description").getAsString();
            break;
        }
        return description.substring(0, 1).toUpperCase() + description.substring(1).toLowerCase();
    }


}
