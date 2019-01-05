package com.kkaysheva.ituniver.domain.mapper;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.model.ContactInfo;

/**
 * ContactInfoToLatLngMapper
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public class ContactInfoToLatLngMapper implements Mapper<ContactInfo, LatLng> {

    @NonNull
    @Override
    public LatLng map(@NonNull ContactInfo contactInfo) {
        return new LatLng(
                Double.parseDouble(contactInfo.getLatitude()),
                Double.parseDouble(contactInfo.getLongitude())
        );
    }
}
