package com.kkaysheva.ituniver.domain.map;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.data.network.directions.GoogleDirectionsService;
import com.kkaysheva.ituniver.data.network.geocode.GeoCodeService;
import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.domain.LocationRepository;
import com.kkaysheva.ituniver.domain.mapper.Mapper;
import com.kkaysheva.ituniver.domain.model.ContactInfo;
import com.kkaysheva.ituniver.domain.stubs.ContactInfoRepositoryStubImpl;
import com.kkaysheva.ituniver.domain.stubs.GeoCodeServiceStubImpl;
import com.kkaysheva.ituniver.domain.stubs.LocationRepositoryStubImpl;
import com.kkaysheva.ituniver.domain.stubs.MapperContactInfoToLatLngStubImpl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * MapInteractorImplTest
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public class MapInteractorImplTest {

    private MapInteractor interactor;

    private ContactInfoRepository contactInfoRepository;

    private GeoCodeService geoCodeService;

    private GoogleDirectionsService googleDirectionsService;

    private Mapper<ContactInfo, LatLng> mapper;

    private LocationRepository locationRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        geoCodeService = new GeoCodeServiceStubImpl();
        contactInfoRepository = new ContactInfoRepositoryStubImpl();
        mapper = new MapperContactInfoToLatLngStubImpl();
        locationRepository = new LocationRepositoryStubImpl();
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
        TestObserver<String> testObserver = new TestObserver<>();
        interactor.getAddress(latLng).subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertResult(address);
    }

    @Test
    public void whenCallSaveAddressThenAddressSaved() {
        int contactId = 1;
        LatLng latLng = new LatLng(53.198438, 56.843609);
        String address = "Izhevsk, Lenina, 1";
        TestObserver<Completable> testObserver = new TestObserver<>();
        interactor.saveAddress(contactId, latLng, address).subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertComplete();
    }

    @Test
    public void whenCallGetLocationsThenReturnLocations() {
        LatLng latLng = new LatLng(53.198438, 56.843609);
        ContactInfo contactInfo = new ContactInfo(
                1,
                "56.843609",
                "53.198438",
                "Izhevsk, Lenina, 1");
        List<LatLng> locations = Arrays.asList(latLng);
        TestObserver<Completable> completableTestObserver = new TestObserver<>();
        contactInfoRepository.insert(contactInfo).subscribe(completableTestObserver);
        completableTestObserver.assertSubscribed();
        completableTestObserver.assertComplete();
        TestObserver<List<LatLng>> testObserver = new TestObserver<>();
        interactor.getLocations().subscribe(testObserver);
        testObserver.assertSubscribed();
        //noinspection unchecked
        testObserver.assertResult(locations);
    }

    @Test
    public void whenCallGetLocationByIdThenReturnLocation() {
        LatLng latLng = new LatLng(53.198438, 56.843609);
        ContactInfo contactInfo = new ContactInfo(1,
                "56.843609",
                "53.198438",
                "Izhevsk, Lenina, 1");
        TestObserver<Completable> completableTestObserver = new TestObserver<>();
        contactInfoRepository.insert(contactInfo).subscribe(completableTestObserver);
        completableTestObserver.assertSubscribed();
        completableTestObserver.assertComplete();
        TestObserver<LatLng> testObserver = new TestObserver<>();
        interactor.getLocationById(1).subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertResult(latLng);
    }

    @Test
    public void whenCallGetLocationByInvalidIdThenReturnError() {
        TestObserver<LatLng> testObserver = new TestObserver<>();
        interactor.getLocationById(1).subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertNoValues();
        testObserver.assertNoErrors();
    }

    @Ignore
    @Test
    public void whenCallGetDeviceLocationThenReturnLocation() {
        String fakeProvider = "fake_provider";
        Location location = new Location(fakeProvider);
        when(locationRepository.getDeviceLocation()).thenReturn(Maybe.just(location));
        assertThat(interactor.getDeviceLocation(), instanceOf(Maybe.class));
        assertThat(interactor.getDeviceLocation().blockingGet(), is(location));
    }

    @Ignore
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