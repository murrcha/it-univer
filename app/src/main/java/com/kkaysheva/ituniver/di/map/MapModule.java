package com.kkaysheva.ituniver.di.map;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.data.database.ContactInfoRepositoryRoom;
import com.kkaysheva.ituniver.data.network.directions.GoogleDirectionsService;
import com.kkaysheva.ituniver.data.network.directions.GoogleDirectionsServiceRetrofit;
import com.kkaysheva.ituniver.data.network.geocode.GeoCodeService;
import com.kkaysheva.ituniver.data.network.geocode.GeoCodeServiceRetrofit;
import com.kkaysheva.ituniver.di.scope.FragmentScope;
import com.kkaysheva.ituniver.domain.map.MapInteractor;
import com.kkaysheva.ituniver.domain.map.MapInteractorImpl;
import com.kkaysheva.ituniver.domain.mapper.ContactInfoToLatLngMapper;
import com.kkaysheva.ituniver.domain.mapper.Mapper;
import com.kkaysheva.ituniver.domain.model.ContactInfo;

import dagger.Module;
import dagger.Provides;

/**
 * MapModule
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@Module
public final class MapModule {

    @Provides
    @FragmentScope
    public Mapper<ContactInfo, LatLng> provideMapper() {
        return new ContactInfoToLatLngMapper();
    }

    @Provides
    @FragmentScope
    public ContactInfoRepository provideContactInfoRepository(ContactInfoRepositoryRoom repository) {
        return repository;
    }

    @Provides
    @FragmentScope
    public GeoCodeService provideGeoCodeService(GeoCodeServiceRetrofit service) {
        return service;
    }

    @Provides
    @FragmentScope
    public GoogleDirectionsService provideGoogleDirectionsService(GoogleDirectionsServiceRetrofit service) {
        return service;
    }

    @Provides
    @FragmentScope
    public MapInteractor provideInteractor(MapInteractorImpl interactor) {
        return interactor;
    }
}
