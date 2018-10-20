//package com.traildog.model;
//
//import android.content.pm.PackageManager;
//
//import com.traildog.app.HomeScreen;
//
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class RadarTests {
//
//    final String testRadarKey = "prj_test_sk_c58a048476f055ba10a7d7dccdf1721d52124183";
//
//    @Test
//    public void testCreateGeofence() {
//
//        double longitude = Math.random() * 360 - 180;
//        double latitude = Math.random() * 180 - 90;
//        int radius = (int)(Math.random() * 2000 + 100);
//        String tag = "testTag" + longitude;
//        String description = "testDescription" + radius;
//        String externalId = "testExtId" + latitude;
//        Map<String, String> metadata = new HashMap<>();
//        metadata.put("testField", "testValue");
//
//
//        RadarGeofence responseGeofence = null;
//
//
//
//        try {
//            responseGeofence = RadarAPI.createCircularGeofence(testRadarKey, latitude, longitude, radius, tag, externalId,description, metadata);
//            System.out.println(responseGeofence);
//        } catch (IOException | PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            assert(false);
//        }
//
//        if (responseGeofence == null) {
//            assert(false);
//        } else {
//            if (!responseGeofence.getType().equals("circle")) {
//                assert(false);
//            } else if (Math.abs(responseGeofence.getCenterLatitude() - latitude) >= 0.001 || Math.abs(responseGeofence.getCenterLongitude() - longitude) >= 0.001) {
//                assert(false);
//            } else if (!responseGeofence.getDescription().equals(description) || !responseGeofence.getTag().equals(tag)
//                    || !responseGeofence.getExternalId().equals(externalId)) {
//                assert(false);
//            } else if (!responseGeofence.getMetadata().containsKey("testField") || !responseGeofence.getMetadata().containsValue("testValue")) {
//                assert(false);
//            } else {
//                assert(true);
//            }
//        }
//
//    }
//
//    @Test
//    public void testGetGeofenceList() {
//        try {
//            Map<String, String> params = RadarUtils.getGeofenceListParams(null, null, null, null);
//            List<RadarGeofence> geofenceList = RadarAPI.getGeofenceList(testRadarKey, params);
//            for (RadarGeofence rg : geofenceList) {
//                System.out.println(rg.toString());
//            }
//            assert(true);
//        } catch (IOException | PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            assert(false);
//        }
//    }
//
//}
