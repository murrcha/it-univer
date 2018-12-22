package com.kkaysheva.ituniver.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * ContactInfo
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@Entity(tableName = "contacts")
public final class ContactInfo {

    @PrimaryKey
    private final int id;
    private final String longitude;
    private final String latitude;
    private final String address;

    public ContactInfo(int id, String longitude, String latitude, String address) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
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

    public String getAddress() {
        return address;
    }
}
