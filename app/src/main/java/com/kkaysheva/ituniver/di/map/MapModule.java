package com.kkaysheva.ituniver.di.map;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.data.provider.contacts.ContactFetcher;
import com.kkaysheva.ituniver.data.provider.location.LocationFetcher;
import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.data.database.ContactInfoRepositoryRoom;
import com.kkaysheva.ituniver.data.network.directions.GoogleDirectionsService;
import com.kkaysheva.ituniver.data.network.directions.GoogleDirectionsServiceRetrofit;
import com.kkaysheva.ituniver.data.network.geocode.GeoCodeService;
import com.kkaysheva.ituniver.data.network.geocode.GeoCodeServiceRetrofit;
import com.kkaysheva.ituniver.di.scope.FragmentScope;
import com.kkaysheva.ituniver.domain.ContactRepository;
import com.kkaysheva.ituniver.domain.LocationRepository;
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

    @FragmentScope
    @Provides
    public Mapper<ContactInfo, LatLng> provideMapper() {
        return new ContactInfoToLatLngMapper();
    }

    @FragmentScope
    @Provides
    public ContactInfoRepository provideContactInfoRepository(ContactInfoRepositoryRoom repository) {
        return repository;
    }

    @FragmentScope
    @Provides
    public GeoCodeService provideGeoCodeService(GeoCodeServiceRetrofit service) {
        return service;
    }

    @FragmentScope
    @Provides
    public GoogleDirectionsService provideGoogleDirectionsService(GoogleDirectionsServiceRetrofit service) {
        return service;
    }

    @FragmentScope
    @Provides
    public MapInteractor provideInteractor(MapInteractorImpl interactor) {
        return interactor;
    }

    @FragmentScope
    @Provides
    public FusedLocationProviderClient provideFusedLocationProviderClient(@NonNull Context context) {
        return LocationServices.getFusedLocationProviderClient(context);
    }

    @FragmentScope
    @Provides
    public ContactRepository provideContactRepository(@NonNull ContactFetcher repository) {
        return repository;
    }

    @FragmentScope
    @Provides
    public LocationRepository provideLocationRepository(@NonNull LocationFetcher repository) {
        return repository;
    }
}
