package com.traildog.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RadarAPI {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String GeofencesURL = "https://api.radar.io/v1/geofences/";

    public static List<RadarGeofence> getGeofenceList(Map<String, Object> myMap) throws IOException {
        // optional parameters:
        // limit (int from 1 to 1000, default 100)
        // createdBefore (datetime)
        // createdAfter (datetime)
        // tag (string)

        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                    .url(GeofencesURL)
                    .build();

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();

        return null;
    }



}
