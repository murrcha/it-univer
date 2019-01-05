package com.kkaysheva.ituniver.domain.mapper;

import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.network.model.GeoCodeResponse;

/**
 * GeoCodeResponseToStringMapper
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class GeoCodeResponseToStringMapper implements Mapper<GeoCodeResponse, String> {

    private static final int FIRST_ELEMENT = 0;

    @NonNull
    @Override
    public String map(@NonNull GeoCodeResponse response) {
        return response
                .getResponse()
                .getGeoObjectCollection()
                .getFeatureMember()
                .get(FIRST_ELEMENT)
                .getGeoObject()
                .getMetaDataProperty()
                .getGeocoderMetaData()
                .getAddressDetails()
                .getCountry()
                .getAddressLine();
    }
}
