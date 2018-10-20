package com.traildog.model;

import java.util.Date;
import java.util.Map;

public class RadarGeofence {

    private String radarId; // radar's auto-generated id
    private Date createdAt; // when geofence was created
    private boolean live; // if this geofence is live (vs a test)

    // (tag + externalId must be unique combo)
    private String tag; // our category for this geofence
    private String externalId; // our assigned id

    private String description;
    private String type; // circle or polygon
    private double centerLongitude;
    private double centerLatitude;
    private Map<String, String> metadata;
    private String userId; // if we want to limit geofence to only a certain user
    private boolean enabled; // if the geofence should generate events, true by default


    public RadarGeofence() {
        // empty constructor for now
    }


    public String getRadarId() {
        return radarId;
    }

    public void setRadarId(String radarId) {
        this.radarId = radarId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCenterLongitude() {
        return centerLongitude;
    }

    public void setCenterLongitude(double centerLongitude) {
        this.centerLongitude = centerLongitude;
    }

    public double getCenterLatitude() {
        return centerLatitude;
    }

    public void setCenterLatitude(double centerLatitude) {
        this.centerLatitude = centerLatitude;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}


/*

_id (string): The unique ID for the geofence, provided by Radar. An alphanumeric string.
createdAt (datetime): The datetime when the geofence was created.
live (boolean): true if the geofence was created with your live API key, false if the user was created with your test API key.
tag (string): An optional group for the geofence.
externalId (string): An optional external ID for the geofence that maps to your internal database.
description (string): A description for the geofence.
type (string): The type of geofence geometry, either polygon or circle.
geometry (GeoJSON): The geometry of the geofence. Coordinates for type polygon. A calculated polygon approximation for type circle. A Polygon in GeoJSON format.
geometryCenter (GeoJSON): The center of the circle for type circle. The calculated centroid of the polygon for type polygon. A Point in GeoJSON format.
geometryRadius (number): The radius of the circle in meters for type circle.
metadata (dictionary): An optional set of custom key-value pairs for the geofence.
userId (string): An optional user restriction for the geofence. If set, the geofence will only generate events for the specified user. If not set, the geofence will generate events for all users.
enabled (boolean): If true, the geofence will generate events. If false, the geofence will not generate events. Defaults to true.

 */

