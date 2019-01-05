package com.kkaysheva.ituniver.di.map;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.di.scope.FragmentScope;
import com.kkaysheva.ituniver.domain.mapper.ContactInfoToLatLngMapper;
import com.kkaysheva.ituniver.domain.mapper.Mapper;
import com.kkaysheva.ituniver.model.ContactInfo;

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
}
