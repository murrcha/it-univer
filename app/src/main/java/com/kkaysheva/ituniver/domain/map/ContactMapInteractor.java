package com.kkaysheva.ituniver.domain.map;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.database.ContactRepository;
import com.kkaysheva.ituniver.model.ContactInfo;
import com.kkaysheva.ituniver.network.GeoCodeServiceRetrofit;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * ContactMapInteractor
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public final class ContactMapInteractor {

    @NonNull
    private final GeoCodeServiceRetrofit service;

    @NonNull
    private final ContactRepository repository;

    @Inject
    public ContactMapInteractor(@NonNull GeoCodeServiceRetrofit service, @NonNull ContactRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    public Single<String> getAddress(LatLng latLng) {
        String latLngString = String.format("%s,%s", latLng.longitude, latLng.latitude);
        return service.loadGeoCode(latLngString);
    }

    public Completable saveAddress(int contactId, LatLng latLng, String address) {
        String longitude = String.valueOf(latLng.longitude);
        String latitude = String.valueOf(latLng.latitude);
        ContactInfo contactInfo = new ContactInfo(contactId, longitude, latitude, address);
        return repository.insert(contactInfo);
    }

    public Single<LatLng> getLatLngById(int contactId) {
        return repository.getById((long) contactId)
                .flatMap(contactInfo ->
                        Single.just(convertContactInfoToLatLng(contactInfo))
                );
    }

    public Single<List<LatLng>> getLatLngAll() {
        List<LatLng> locations = new ArrayList<>();
        return repository.getAll()
                .flatMap(contactInfoList -> {
                    for (ContactInfo contactInfo : contactInfoList) {
                        locations.add(convertContactInfoToLatLng(contactInfo));
                    }
                    return Single.just(locations);
                });
    }

    private LatLng convertContactInfoToLatLng(@NonNull ContactInfo contactInfo) {
        return new LatLng(
            Double.parseDouble(contactInfo.getLatitude()),
            Double.parseDouble(contactInfo.getLongitude())
        );
    }
}
