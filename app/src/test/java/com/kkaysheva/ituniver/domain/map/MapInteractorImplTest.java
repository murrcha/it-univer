package com.kkaysheva.ituniver.domain.map;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.data.network.directions.GoogleDirectionsService;
import com.kkaysheva.ituniver.data.network.geocode.GeoCodeService;
import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.domain.LocationRepository;
import com.kkaysheva.ituniver.domain.mapper.Mapper;
import com.kkaysheva.ituniver.domain.model.ContactInfo;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * MapInteractorImplTest
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public class MapInteractorImplTest {

    private MapInteractor interactor;

    @Mock
    private ContactInfoRepository contactInfoRepository;

    @Mock
    private GeoCodeService geoCodeService;

    @Mock
    private GoogleDirectionsService googleDirectionsService;

    @Mock
    private Mapper<ContactInfo, LatLng> mapper;

    @Mock
    private LocationRepository locationRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = new MapInteractorImpl(
                geoCodeService,
                contactInfoRepository,
                googleDirectionsService,
                mapper,
                locationRepository);
    }

    @Test
    public void whenCallGetAddressThenReturnAddressString() {
        LatLng latLng = new LatLng(53.198438, 56.843609);
        String address = "Izhevsk, Lenina, 1";
        Single<String> response = Single.just(address);
        when(geoCodeService.loadGeoCode(latLng)).thenReturn(response);
        assertThat(interactor.getAddress(latLng), instanceOf(Single.class));
        assertThat(interactor.getAddress(latLng).blockingGet(), is(address));
    }

    @Test
    public void whenCallSaveAddressThenAddressSaved() {
        int contactId = 1;
        LatLng latLng = new LatLng(53.198438, 56.843609);
        String address = "Izhevsk, Lenina, 1";
        interactor.saveAddress(contactId, latLng, address);
        verify(contactInfoRepository).insert(any(ContactInfo.class));
    }

    @Test
    public void whenCallGetLocationsThenReturnLocations() {
        int contactId = 1;
        LatLng latLng = new LatLng(53.198438, 56.843609);
        String latitude = String.valueOf(latLng.latitude);
        String longitude = String.valueOf(latLng.longitude);
        String address = "Izhevsk, Lenina, 1";
        List<ContactInfo> contactInfoList = new ArrayList<>();
        ContactInfo contactInfo = new ContactInfo(contactId, longitude, latitude, address);
        contactInfoList.add(contactInfo);
        when(contactInfoRepository.getAll()).thenReturn(Single.just(contactInfoList));
        when(mapper.map(contactInfo)).thenReturn(latLng);
        assertThat(interactor.getLocations(), instanceOf(Single.class));
        assertThat(interactor.getLocations().blockingGet().get(0).latitude, is(53.198438));
        assertThat(interactor.getLocations().blockingGet().size(), is(1));
    }

    @Test
    public void whenCallGetLocationByIdThenReturnLocation() {
        int contactId = 1;
        LatLng latLng = new LatLng(53.198438, 56.843609);
        String latitude = String.valueOf(latLng.latitude);
        String longitude = String.valueOf(latLng.longitude);
        String address = "Izhevsk, Lenina, 1";
        ContactInfo contactInfo = new ContactInfo(contactId, longitude, latitude, address);
        when(mapper.map(contactInfo)).thenReturn(latLng);
        when(contactInfoRepository.getById(1L)).thenReturn(Maybe.just(contactInfo));
        assertThat(interactor.getLocationById(1), instanceOf(Maybe.class));
        assertThat(interactor.getLocationById(1).blockingGet(), is(latLng));
    }

    @Test
    public void whenCallGetLocationByInvalidIdThenReturnError() {
        int contactId = 1;
        when(contactInfoRepository.getById((long) contactId)).thenReturn(Maybe.empty());
        assertThat(interactor.getLocationById(contactId), instanceOf(Maybe.class));
        assertThat(interactor.getLocationById(contactId).blockingGet(), nullValue());
    }

    @Test
    public void whenCallGetDeviceLocationThenReturnLocation() {
        String fakeProvider = "fake_provider";
        Location location = new Location(fakeProvider);
        when(locationRepository.getDeviceLocation()).thenReturn(Maybe.just(location));
        assertThat(interactor.getDeviceLocation(), instanceOf(Maybe.class));
        assertThat(interactor.getDeviceLocation().blockingGet(), is(location));
    }

    @Test
    public void whenCallGetDirectionsThenReturnDirections() {
        LatLng origin = new LatLng(53.198438, 56.843609);
        LatLng destination = new LatLng(53.198438, 56.843609);
        List<LatLng> route = new ArrayList<>();
        route.add(origin);
        route.add(destination);
        Single<List<LatLng>> response = Single.just(route);
        when(googleDirectionsService.loadDirections(origin, destination)).thenReturn(response);
        assertThat(interactor.getDirections(origin, destination), instanceOf(Single.class));
        assertThat(interactor.getDirections(origin, destination).blockingGet().size(), is(2));
    }
}