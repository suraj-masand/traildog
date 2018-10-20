package com.traildog.model;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

public class RadarUtils {

    final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static Map<String, Object> getGeofenceListParams(@Nullable Integer limit,
                                                            @Nullable String createdBefore,
                                                            @Nullable String createdAfter,
                                                            @Nullable String tag) {
        // optional parameters:
        // limit (int from 1 to 1000, default 100)
        // createdBefore (datetime)
        // createdAfter (datetime)
        // tag (string)

        Map<String, Object> params = new HashMap<>();

        if (limit != null && limit > 0 && limit <= 1000) {
            params.put("limit", limit);
        }

        if (createdBefore != null && !createdBefore.isEmpty()) {
            try {
                Date createdBeforeDate = formatter.parse(createdBefore);
                params.put("createdBefore", createdBeforeDate);
            } catch (ParseException e) {
                Log.d("ParseException", "Could not parse createdBefore string");
            }
        }

        if (createdAfter != null && !createdAfter.isEmpty()) {
            try {
                Date createdAfterDate = formatter.parse(createdAfter);
                params.put("createdAfter", createdAfterDate);
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


    public static RadarGeofence parseGeofenceFromJSON(String jsonString) {

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            RadarGeofence geofence = new RadarGeofence();
            geofence.setRadarId(jsonObject.getString("_id"));
            geofence.setCreatedAt(formatter.parse(jsonObject.getString("createdAt")));
            geofence.setLive(jsonObject.getBoolean("live"));
            geofence.setTag(jsonObject.getString("tag"));
            geofence.setExternalId(jsonObject.getString("externalId"));
            geofence.setDescription(jsonObject.getString("description"));
            geofence.setType(jsonObject.getString("type"));
            if (geofence.getType() == "circle") {
                geofence.setRadius(jsonObject.getInt("geometryRadius"));
            }

            JSONObject geometryCenter = jsonObject.getJSONObject("geometryCenter");
            JSONArray coordinates = geometryCenter.getJSONArray("coordinates");
            geofence.setCenterLongitude(coordinates.getInt(0));
            geofence.setCenterLatitude(coordinates.getInt(1));
            if (jsonObject.has("metadata")) {
                Map<String, String> metaMap = new HashMap<>();
                JSONObject metadataJson = jsonObject.getJSONObject("metadata");

                Iterator<String> keys = metadataJson.keys();

                while(keys.hasNext()) {
                    String key = keys.next();
                    String value = "" + metadataJson.get(key);
                    metaMap.put(key, value);
                }
                geofence.setMetadata(metaMap);
            }


            return geofence;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
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




}
