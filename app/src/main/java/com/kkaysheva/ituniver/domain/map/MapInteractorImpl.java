package com.kkaysheva.ituniver.domain.map;

import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.data.network.directions.GoogleDirectionsService;
import com.kkaysheva.ituniver.data.network.geocode.GeoCodeService;
import com.kkaysheva.ituniver.data.provider.location.LocationFetcher;
import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.domain.mapper.Mapper;
import com.kkaysheva.ituniver.domain.model.ContactInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * MapInteractorImpl
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public final class MapInteractorImpl implements MapInteractor {

    @NonNull
    private final GeoCodeService geoService;

    @NonNull
    private final ContactInfoRepository repository;

    @NonNull
    private final GoogleDirectionsService googleService;

    @NonNull
    private final Mapper<ContactInfo, LatLng> mapper;

    @NonNull
    private final LocationFetcher locationFetcher;

    @Inject
    public MapInteractorImpl(@NonNull GeoCodeService geoService,
                             @NonNull ContactInfoRepository repository,
                             @NonNull GoogleDirectionsService googleService,
                             @NonNull Mapper<ContactInfo, LatLng> mapper,
                             @NonNull LocationFetcher locationFetcher) {
        this.geoService = geoService;
        this.repository = repository;
        this.googleService = googleService;
        this.mapper = mapper;
        this.locationFetcher = locationFetcher;
    }

    @NonNull
    @Override
    public Single<String> getAddress(@NonNull LatLng latLng) {
        String latLngString = String.format("%s,%s", latLng.longitude, latLng.latitude);
        return geoService.loadGeoCode(latLngString);
    }

    @NonNull
    @Override
    public Completable saveAddress(int contactId, @NonNull LatLng latLng, @NonNull String address) {
        String longitude = String.valueOf(latLng.longitude);
        String latitude = String.valueOf(latLng.latitude);
        ContactInfo contactInfo = new ContactInfo(contactId, longitude, latitude, address);
        return repository.insert(contactInfo);
    }

    @NonNull
    @Override
    public Maybe<LatLng> getLocationById(int contactId) {
        return repository.getById((long) contactId)
                .map(mapper::map);
    }

    @NonNull
    @Override
    public Single<List<LatLng>> getLocations() {
        return repository.getAll()
                .map(contactInfoList -> {
                    List<LatLng> locations = new ArrayList<>();
                    for (ContactInfo contactInfo : contactInfoList) {
                        locations.add(mapper.map(contactInfo));
                    }
                    return locations;
                });
    }

    @NonNull
    @Override
    public Single<List<LatLng>> getDirections(@NonNull LatLng origin, @NonNull LatLng destination) {
        String originString = String.format("%s,%s", origin.latitude, origin.longitude);
        String destinationString = String.format("%s,%s", destination.latitude, destination.longitude);
        return googleService.loadDirections(originString, destinationString);
    }

    @NonNull
    @Override
    public Maybe<Location> getDeviceLocation() {
        return locationFetcher.getDeviceLocation();
    }
}
