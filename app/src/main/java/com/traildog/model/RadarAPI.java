package com.traildog.model;

import android.content.pm.PackageManager;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RadarAPI {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String GeofencesURL = "https://api.radar.io/v1/geofences/";
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

    public static RadarGeofence createCircularGeofence(String radarKey, double latitude, double longitude, int radius,
                                                 String tag, String externalId, String description,
                                                 Map<String, String> metadata) throws IOException, PackageManager.NameNotFoundException {



        String jsonRequest = "{";
        jsonRequest += "\"description\": \"" + description + "\",\n";
        jsonRequest += "\"type\": \"circle\",\n";
        jsonRequest += "\"coordinates\": [" + String.format("%.9f", longitude) + ", " + String.format("%.9f", latitude) + "],\n";
        jsonRequest += "\"radius\": " + radius + ",\n";
        jsonRequest += "\"tag\": \"" + tag + "\",\n";
        jsonRequest += "\"externalId\": \"" + externalId + "\",\n";
        if (metadata != null && metadata.size() > 0) {
            jsonRequest += "\"metadata\": {";

            for (Map.Entry<String, String> entry : metadata.entrySet()) {
                jsonRequest += "\"" + entry.getKey() + "\": \"" + entry.getValue() + "\",\n";
            }
            jsonRequest = jsonRequest.substring(0, jsonRequest.length() - 2);
            jsonRequest += "}\n";
        }
        jsonRequest += "}";

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(JSON, jsonRequest);

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
            RadarGeofence radarGeofence = RadarUtils.parseGeofenceFromJSONString(startGeofence);
            return radarGeofence;
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
