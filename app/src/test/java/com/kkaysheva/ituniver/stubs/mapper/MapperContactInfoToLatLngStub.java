package com.kkaysheva.ituniver.stubs.mapper;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.domain.mapper.Mapper;
import com.kkaysheva.ituniver.domain.model.ContactInfo;

public final class MapperContactInfoToLatLngStub implements Mapper<ContactInfo, LatLng> {

    @NonNull
    @Override
    public LatLng map(@NonNull ContactInfo object) {
        return new LatLng(53.198438, 56.843609);
    }
}
