package com.kkaysheva.ituniver.domain.map;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.database.ContactRepository;
import com.kkaysheva.ituniver.domain.mapper.Mapper;
import com.kkaysheva.ituniver.model.ContactInfo;
import com.kkaysheva.ituniver.network.GeoCodeServiceRetrofit;
import com.kkaysheva.ituniver.network.GoogleDirectionsServiceRetrofit;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * ContactMapInteractor
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public final class ContactMapInteractor {

    @NonNull
    private final GeoCodeServiceRetrofit geoService;

    @NonNull
    private final ContactRepository repository;

    @NonNull
    private final GoogleDirectionsServiceRetrofit googleService;

    @NonNull
    private final Mapper<ContactInfo, LatLng> mapper;

    @Inject
    public ContactMapInteractor(@NonNull GeoCodeServiceRetrofit service,
                                @NonNull ContactRepository repository,
                                @NonNull GoogleDirectionsServiceRetrofit googleService,
                                @NonNull Mapper<ContactInfo, LatLng> mapper) {
        this.geoService = service;
        this.repository = repository;
        this.googleService = googleService;
        this.mapper = mapper;
    }

    public Single<String> getAddress(LatLng latLng) {
        String latLngString = String.format("%s,%s", latLng.longitude, latLng.latitude);
        return geoService.loadGeoCode(latLngString);
    }

    public Completable saveAddress(int contactId, LatLng latLng, String address) {
        String longitude = String.valueOf(latLng.longitude);
        String latitude = String.valueOf(latLng.latitude);
        ContactInfo contactInfo = new ContactInfo(contactId, longitude, latitude, address);
        return repository.insert(contactInfo);
    }

    public Maybe<LatLng> getLatLngById(int contactId) {
        return repository.getById((long) contactId)
                .flatMap(contactInfo ->
                        Maybe.just(mapper.map(contactInfo))
                );
    }

    public Single<List<LatLng>> getLatLngAll() {
        List<LatLng> locations = new ArrayList<>();
        return repository.getAll()
                .flatMap(contactInfoList -> {
                    for (ContactInfo contactInfo : contactInfoList) {
                        locations.add(mapper.map(contactInfo));
                    }
                    return Single.just(locations);
                });
    }

    public Single<List<LatLng>> getDirections(LatLng origin, LatLng destination) {
        String originString = String.format("%s,%s", origin.latitude, origin.longitude);
        String destinationString = String.format("%s,%s", destination.latitude, destination.longitude);
        return googleService.loadDirections(originString, destinationString);
    }
}
