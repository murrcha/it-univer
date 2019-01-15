package com.kkaysheva.ituniver.domain.mapper;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.kkaysheva.ituniver.data.network.directions.DirectionsResponse;

import java.util.Collections;
import java.util.List;

/**
 * DirectionsResponseToListLatLngMapper
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class DirectionsResponseToListLatLngMapper implements Mapper<DirectionsResponse, List<LatLng>> {

    private static final int FIRST_ELEMENT = 0;

    @NonNull
    @Override
    public List<LatLng> map(@NonNull DirectionsResponse response) {
        String polylineEncoded = response
                .getRoutes()
                .get(FIRST_ELEMENT)
                .getOverviewPolyLine()
                .getPoints();
        return Collections.unmodifiableList(PolyUtil.decode(polylineEncoded));
    }
}
