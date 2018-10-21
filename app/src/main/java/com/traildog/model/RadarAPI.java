package com.traildog.model;

import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.radar.sdk.model.RadarUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import io.radar.sdk.model.RadarGeofence;
import io.radar.sdk.model.RadarGeofenceGeometry;
import io.radar.sdk.model.RadarCircleGeometry;
import io.radar.sdk.model.Coordinate;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class RadarAPI {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String GeofencesURL = "https://api.radar.io/v1/geofences/";
    public static final String UserURL = "https://api.radar.io/v1/users/12345";

    public static final String RADAR_KEY_TYPE = "radar-api-test-key"; // either "radar-api-test-key" or "radar-api-live-key"

    public static List<RadarGeofence> getGeofenceList(String radarKey, Map<String, String> paramMap) throws IOException, PackageManager.NameNotFoundException {
        // optional parameters:
        // limit (int from 1 to 1000, default 100)
        // createdBefore (datetime)
        // createdAfter (datetime)
        // tag (string)

        OkHttpClient client = new OkHttpClient();

        String queryParams = "";
        if (paramMap != null && paramMap.size() > 0) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                if (queryParams.isEmpty()) {
                    queryParams += "?";
                } else {
                    queryParams += "&";
                }
                queryParams += entry.getKey() + "=" + entry.getValue();
            }
        }

        Request request = new Request.Builder()
                    .url(GeofencesURL + queryParams)
                    .addHeader("Authorization",  radarKey)
                    .get()
                    .build();

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();

        String startGeofenceList = RadarUtils.getStartGeofenceList(responseString);
        if (startGeofenceList.isEmpty()) {
            Log.e("Error", "Could not find start of geofences list.");
        } else {
            List<RadarGeofence> radarGeofenceList = RadarUtils.parseGeofenceListFromJSON(startGeofenceList);
            return radarGeofenceList;
        }

        return null;
    }

    public static RadarUser updateUserLocation(String radarKey, String userId, String description, Location loc) throws IOException {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("description", description);
        params.put("longitude", String.valueOf(loc.getLongitude()));
        params.put("latitude", String.valueOf(loc.getLatitude()));
        params.put("accuracy", String.valueOf(loc.getAccuracy()));
        String userJson = gson.toJson(params);

//        RadarUser user = new RadarUser("5bcbd656591844002ac7a9a3", userId, null, description, null,loc, null, null, null, false, false);
//        String userJson = gson.toJson(user);
        System.out.println(userJson);

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(JSON, userJson);

        Request request = new Request.Builder()
                .url(UserURL)
                .header("Authorization", radarKey)
                .addHeader("Content-Type", "application/json")
                .put(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        System.out.println(responseString);

        String startUser = RadarUtils.getStartUser(responseString);
        if (!startUser.isEmpty()) {
            System.out.println(startUser);
            return gson.fromJson(startUser, RadarUser.class);
        }

        return null;

    }

    public static RadarGeofence createCircularGeofence(String radarKey, double latitude, double longitude, int radius,
                                                 String tag, String externalId, String description,
                                                 Map<String, String> metadata) throws IOException, PackageManager.NameNotFoundException {

        JSONObject metadataJson = null;
        Gson gson = new Gson();
        try {
            metadataJson = new JSONObject(gson.toJson(metadata));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RadarGeofenceGeometry geometry = new RadarCircleGeometry(new Coordinate(latitude, longitude), radius);
        RadarGeofence radarGeofence = new RadarGeofence(null, description, tag, externalId, metadataJson, geometry);

        String geofenceJsonString = gson.toJson(radarGeofence);

//        String jsonRequest = "{";
//        jsonRequest += "\"description\": \"" + description + "\",\n";
//        jsonRequest += "\"type\": \"circle\",\n";
//        jsonRequest += "\"coordinates\": [" + String.format("%.9f", longitude) + ", " + String.format("%.9f", latitude) + "],\n";
//        jsonRequest += "\"radius\": " + radius + ",\n";
//        jsonRequest += "\"tag\": \"" + tag + "\",\n";
//        jsonRequest += "\"externalId\": \"" + externalId + "\",\n";
//        if (metadata != null && metadata.size() > 0) {
//            jsonRequest += "\"metadata\": {";
//
//            for (Map.Entry<String, String> entry : metadata.entrySet()) {
//                jsonRequest += "\"" + entry.getKey() + "\": \"" + entry.getValue() + "\",\n";
//            }
//            jsonRequest = jsonRequest.substring(0, jsonRequest.length() - 2);
//            jsonRequest += "}\n";
//        }
//        jsonRequest += "}";

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(JSON, geofenceJsonString);

        Request request = new Request.Builder()
                .url(GeofencesURL)
                .header("Authorization", radarKey)
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();

        String startGeofence = RadarUtils.getStartGeofence(responseString);
        if (!startGeofence.isEmpty()) {
            return gson.fromJson(startGeofence, RadarGeofence.class);
        }
        /*
        {
            "description": "my description test 2",
                "type": "circle",
                "coordinates": [-49.3965708, -35.7769895],
            "radius": 800,
                "tag": "testing",
                "externalId": "random spot test 9",
                "metadata": { "oh my gosh": "hello1", "testing": 345678 }
        }
        */
        return null;
    }

//    private static String getRadarKey(Context context, String keyType) throws PackageManager.NameNotFoundException {
//        String radarKey = null;
//        ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
//        if (appInfo.metaData != null) {
//            radarKey = appInfo.metaData.getString(RADAR_KEY_TYPE);
//        }
//        return radarKey;
//    }

//    private static OkHttpClient getUnsafeOkHttpClient() {
//        try {
//            // Create a trust manager that does not validate certificate chains
//            final TrustManager[] trustAllCerts = new TrustManager[] {
//                    new X509TrustManager() {
//                        @Override
//                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                        }
//
//                        @Override
//                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                            return new java.security.cert.X509Certificate[]{};
//                        }
//                    }
//            };
//
//            // Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            // Create an ssl socket factory with our all-trusting manager
//            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
//            builder.hostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            });
//
//            OkHttpClient okHttpClient = builder.build();
//            return okHttpClient;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

}
