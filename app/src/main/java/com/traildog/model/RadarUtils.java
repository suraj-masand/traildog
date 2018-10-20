package com.traildog.model;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import android.content.Context;
import android.provider.Settings;
import android.widget.Toast;

import com.google.gson.Gson;
import com.traildog.app.MyRadarReceiver;

import io.radar.sdk.Radar;
import io.radar.sdk.model.RadarEvent;
import io.radar.sdk.model.RadarGeofence;

public class RadarUtils {

    final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static Map<String, String> getGeofenceListParams(@Nullable Integer limit,
                                                            @Nullable String createdBefore,
                                                            @Nullable String createdAfter,
                                                            @Nullable String tag) {
        // optional parameters:
        // limit (int from 1 to 1000, default 100)
        // createdBefore (datetime)
        // createdAfter (datetime)
        // tag (string)

        Map<String, String> params = new HashMap<>();

        if (limit != null && limit > 0 && limit <= 1000) {
            params.put("limit", String.valueOf(limit));
        }

        if (createdBefore != null && !createdBefore.isEmpty()) {
            try {
                Date createdBeforeDate = formatter.parse(createdBefore); // validates date
                params.put("createdBefore", createdBefore);
            } catch (ParseException e) {
                Log.d("ParseException", "Could not parse createdBefore string");
            }
        }

        if (createdAfter != null && !createdAfter.isEmpty()) {
            try {
                Date createdAfterDate = formatter.parse(createdAfter); // validates date
                params.put("createdAfter", createdAfter);
            } catch (ParseException e) {
                Log.d("ParseException", "Could not parse createdAfter string");
            }
        }

        if (tag != null && !tag.isEmpty()) {
            params.put("tag", tag.trim());
        }

        if (params.size() > 0) {
            return params;
        }

        return null;
    }

    public static List<RadarGeofence> parseGeofenceListFromJSON(String jsonString) {
        List<RadarGeofence> geofenceList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JSONArray geofenceJSONList = new JSONArray(jsonString);
            for (int i = 0; i < geofenceJSONList.length(); i++) {
                JSONObject geofenceJSON = geofenceJSONList.getJSONObject(i);
                RadarGeofence geofence = gson.fromJson(geofenceJSON.toString(), RadarGeofence.class);
                geofenceList.add(geofence);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return geofenceList;
    }


//    public static RadarGeofence parseGeofenceFromJSONString(String jsonString) {
//
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = new JSONObject(jsonString);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RadarGeofence geofence = new RadarGeofence();
//        try {
//            geofence.setRadarId(jsonObject.getString("_id"));
//            geofence.setCreatedAt(formatter.parse(jsonObject.getString("createdAt")));
//            geofence.setLive(jsonObject.getBoolean("live"));
//            geofence.setTag(jsonObject.optString("tag"));
//            geofence.setExternalId(jsonObject.optString("externalId"));
//            geofence.setDescription(jsonObject.optString("description", "description is empty"));
//            geofence.setType(jsonObject.getString("type"));
//            if (geofence.getType().equalsIgnoreCase("circle")) {
//                geofence.setRadius(jsonObject.getInt("geometryRadius"));
//            }
//
//            JSONObject geometryCenter = jsonObject.getJSONObject("geometryCenter");
//            JSONArray coordinates = geometryCenter.getJSONArray("coordinates");
//            geofence.setCenterLongitude(coordinates.getDouble(0));
//            geofence.setCenterLatitude(coordinates.getDouble(1));
//            if (jsonObject.has("metadata")) {
//                Map<String, String> metaMap = new HashMap<>();
//                JSONObject metadataJson = jsonObject.getJSONObject("metadata");
//
//                Iterator<String> keys = metadataJson.keys();
//
//                while(keys.hasNext()) {
//                    String key = keys.next();
//                    String value = "" + metadataJson.get(key);
//                    metaMap.put(key, value);
//                }
//                geofence.setMetadata(metaMap);
//            }
//
//            geofence.setEnabled(jsonObject.getBoolean("enabled"));
//            geofence.setUserId(jsonObject.optString("userId"));
//
//            return geofence;
//
//        } catch (JSONException | ParseException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public static String getStartGeofenceList(String jsonString) {
        if (jsonString.isEmpty()) {
            return jsonString;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray geofences = jsonObject.getJSONArray("geofences");
            return geofences.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getStartGeofence(String jsonString) {
        if (jsonString.isEmpty()) {
            return jsonString;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject geofence = jsonObject.getJSONObject("geofence");
            return geofence.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }


        /*

_id (string):
createdAt (datetime):
live (boolean):
tag (string):
externalId (string):
description (string):
type (string):
geometry (GeoJSON):
geometryCenter (GeoJSON):
geometryRadius (number): The radius of the circle in meters for type circle.
metadata (dictionary): An optional set of custom key-value pairs for the geofence.
userId (string): An optional user restriction for the geofence. If set, the geofence will only generate events for the specified user. If not set, the geofence will generate events for all users.
enabled (boolean): If true, the geofence will generate events. If false, the geofence will not generate events. Defaults to true.

 */

    static String getUserId(Context context) {
        if (context == null) {
            return null;
        }

        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String stringForStatus(Radar.RadarStatus status) {
        switch (status) {
            case SUCCESS:
                return "Success";
            case ERROR_PUBLISHABLE_KEY:
                return "Publishable Key Error";
            case ERROR_PERMISSIONS:
                return "Permissions Error";
            case ERROR_LOCATION:
                return "Location Error";
            case ERROR_NETWORK:
                return "Network Error";
            case ERROR_UNAUTHORIZED:
                return "Unauthorized Error";
            case ERROR_SERVER:
                return "Server Error";
            default:
                return "Unknown Error";
        }

    }

    public static String stringForEvent(RadarEvent event) {
        switch (event.getType()) {
            case USER_ENTERED_GEOFENCE:
                return "Entered geofence " + (event.getGeofence() != null ? event.getGeofence().getDescription() : "-");
            case USER_EXITED_GEOFENCE:
                return "Exited geofence " + (event.getGeofence() != null ? event.getGeofence().getDescription() : "-");
            case USER_ENTERED_HOME:
                return "Entered home";
            case USER_EXITED_HOME:
                return "Exited home";
            case USER_ENTERED_OFFICE:
                return "Entered office";
            case USER_EXITED_OFFICE:
                return "Exited office";
            case USER_STARTED_TRAVELING:
                return "Started traveling";
            case USER_STOPPED_TRAVELING:
                return "Stopped traveling";
            case USER_ENTERED_PLACE:
                return "Entered place " + (event.getPlace() != null ? event.getPlace().getName() : "-");
            case USER_EXITED_PLACE:
                return "Exited place " + (event.getPlace() != null ? event.getPlace().getName() : "-");
            default:
                return "-";
        }
    }

//    public static String handleRadarEvent(RadarEvent event) {
//        if (event.getType() == RadarEvent.RadarEventType.USER_ENTERED_GEOFENCE) {
//            String geofenceExternalId = event.getGeofence().getExternalId();
//            String geofenceTag = event.getGeofence().getTag();
//
//        }
//        return null;
//    }



}
