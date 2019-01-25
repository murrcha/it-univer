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
import com.kkaysheva.ituniver.domain.stubs.DirectionsServiceStubImpl;
import com.kkaysheva.ituniver.domain.stubs.GeoCodeServiceStubImpl;
import com.kkaysheva.ituniver.domain.stubs.LocationRepositoryStubImpl;
import com.kkaysheva.ituniver.domain.stubs.MapperContactInfoToLatLngStubImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.observers.TestObserver;

/**
 * MapInteractorImplTest
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class MapInteractorImplTest {

    private MapInteractor interactor;

    private ContactInfoRepository stubContactInfoRepository;

    @Before
    public void before() {
        GeoCodeService stubGeoCodeService = new GeoCodeServiceStubImpl();
        stubContactInfoRepository = new ContactInfoRepositoryStubImpl();
        Mapper<ContactInfo, LatLng> stubMapper = new MapperContactInfoToLatLngStubImpl();
        LocationRepository stubLocationRepository = new LocationRepositoryStubImpl();
        GoogleDirectionsService stubDirectionsService = new DirectionsServiceStubImpl();
        interactor = new MapInteractorImpl(
                stubGeoCodeService,
                stubContactInfoRepository,
                stubDirectionsService,
                stubMapper,
                stubLocationRepository);
    }

    @Test
    public void whenCallGetAddressThenReturnAddressString() {
        LatLng latLng = new LatLng(53.198438, 56.843609);
        String address = "Izhevsk, Lenina, 1";
        TestObserver<String> testObserver = new TestObserver<>();
        interactor.getAddress(latLng).subscribe(testObserver);
        testObserver
                .assertSubscribed()
                .assertResult(address);
    }

    @Test
    public void whenCallSaveAddressThenAddressSaved() {
        int contactId = 1;
        LatLng latLng = new LatLng(53.198438, 56.843609);
        String address = "Izhevsk, Lenina, 1";
        TestObserver<Completable> testObserver = new TestObserver<>();
        interactor.saveAddress(contactId, latLng, address).subscribe(testObserver);
        testObserver
                .assertSubscribed()
                .assertComplete();
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
        stubContactInfoRepository.insert(contactInfo).subscribe(completableTestObserver);
        completableTestObserver
                .assertSubscribed()
                .assertComplete();
        TestObserver<List<LatLng>> testObserver = new TestObserver<>();
        interactor.getLocations().subscribe(testObserver);
        //noinspection unchecked
        testObserver
                .assertSubscribed()
                .assertResult(locations);
    }

    @Test
    public void whenCallGetLocationByIdThenReturnLocation() {
        LatLng latLng = new LatLng(53.198438, 56.843609);
        ContactInfo contactInfo = new ContactInfo(1,
                "56.843609",
                "53.198438",
                "Izhevsk, Lenina, 1");
        TestObserver<Completable> completableTestObserver = new TestObserver<>();
        stubContactInfoRepository.insert(contactInfo).subscribe(completableTestObserver);
        completableTestObserver
                .assertSubscribed()
                .assertComplete();
        TestObserver<LatLng> testObserver = new TestObserver<>();
        interactor.getLocationById(1).subscribe(testObserver);
        testObserver
                .assertSubscribed()
                .assertResult(latLng);
    }

    @Test
    public void whenCallGetLocationByInvalidIdThenReturnError() {
        TestObserver<LatLng> testObserver = new TestObserver<>();
        interactor.getLocationById(1).subscribe(testObserver);
        testObserver
                .assertSubscribed()
                .assertNoValues()
                .assertNoErrors();
    }

    @Test
    public void whenCallGetDeviceLocationThenReturnLocation() {
        TestObserver<Location> testObserver = new TestObserver<>();
        interactor.getDeviceLocation().subscribe(testObserver);
        testObserver
                .assertSubscribed()
                .assertNoErrors()
                .assertComplete();
    }

    @Test
    public void whenCallGetDirectionsThenReturnDirections() {
        LatLng origin = new LatLng(53.198438, 56.843609);
        LatLng destination = new LatLng(53.198438, 56.843609);
        List<LatLng> route = new ArrayList<>();
        route.add(origin);
        route.add(destination);
        TestObserver<List<LatLng>> testObserver = new TestObserver<>();
        interactor.getDirections(origin, destination).subscribe(testObserver);
        //noinspection unchecked
        testObserver
                .assertSubscribed()
                .assertResult(route);
    }
}