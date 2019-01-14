package com.kkaysheva.ituniver.di.app;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.domain.mapper.DirectionsResponseToListLatLngMapper;
import com.kkaysheva.ituniver.domain.mapper.GeoCodeResponseToStringMapper;
import com.kkaysheva.ituniver.domain.mapper.Mapper;
import com.kkaysheva.ituniver.data.network.directions.DirectionsResponse;
import com.kkaysheva.ituniver.data.network.geocode.GeoCodeResponse;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * MapperModule
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
@Module
public final class MapperModule {

    @Singleton
    @Provides
    public Mapper<GeoCodeResponse, String> provideGeoCodeMapper() {
        return new GeoCodeResponseToStringMapper();
    }

    @Singleton
    @Provides
    public Mapper<DirectionsResponse, List<LatLng>> provideDirectionsMapper() {
        return new DirectionsResponseToListLatLngMapper();
    }
}
