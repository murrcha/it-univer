package com.kkaysheva.ituniver.domain.mapper;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.domain.model.ContactInfo;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * ContactInfoToLatLngMapperTest
 *
 * @author Kaysheva Ksenya (murrcha@me.com)
 * @since 01.2019
 */
public class ContactInfoToLatLngMapperTest {

    @Test
    public void whenCallMapForContactInfoThenReturnLatLng() {
        int contactId = 1;
        LatLng latLng = new LatLng(53.198438, 56.843609);
        String latitude = String.valueOf(latLng.latitude);
        String longitude = String.valueOf(latLng.longitude);
        String address = "Izhevsk, Lenina, 1";
        ContactInfo contactInfo = new ContactInfo(contactId, longitude, latitude, address);
        ContactInfoToLatLngMapper mapper = new ContactInfoToLatLngMapper();
        assertThat(mapper.map(contactInfo), is(latLng));
    }
}