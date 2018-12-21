package com.kkaysheva.ituniver.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "contacts")
public final class ContactInfo {

    @PrimaryKey
    private final int id;
    private final String longitude;
    private final String latitude;

    public ContactInfo(int id, String longitude, String latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }
}
