package com.kkaysheva.ituniver.domain.map;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.database.ContactRepository;
import com.kkaysheva.ituniver.model.ContactInfo;
import com.kkaysheva.ituniver.network.GeoCodeServiceRetrofit;

import javax.inject.Inject;

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

    public void saveAddress(int contactId, LatLng latLng, String address) {
        String longitude = String.valueOf(latLng.longitude);
        String latitude = String.valueOf(latLng.latitude);
        ContactInfo contactInfo = new ContactInfo(contactId, longitude, latitude, address);
        repository.update(contactInfo);
    }
}
