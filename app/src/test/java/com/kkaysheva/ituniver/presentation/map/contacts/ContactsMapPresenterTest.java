package com.kkaysheva.ituniver.presentation.map.contacts;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.domain.map.MapInteractor;
import com.kkaysheva.ituniver.rules.TestSchedulerRule;
import com.kkaysheva.ituniver.stubs.interactor.MapInteractorStub;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Objects;

/**
 * ContactsMapPresenterTest
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public class ContactsMapPresenterTest {

    @Rule
    public final TestSchedulerRule testSchedulerRule = new TestSchedulerRule();

    private MapInteractor stubInteractor;

    private ContactsMapPresenter presenter;

    @Mock
    private GoogleMap googleMap;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        stubInteractor = new MapInteractorStub();
        presenter = new ContactsMapPresenter(stubInteractor);
    }

    @Test
    public void whenCallGetLocationsForAll() {
        presenter.getLocationForAll(googleMap);
        stubInteractor.getLocations().test()
                .assertSubscribed()
                .assertValue(latLngs -> latLngs.size() == 2)
                .assertNoErrors()
                .assertComplete()
                .dispose();
    }

    @Test
    public void whenCallGetRoute() {
        LatLng origin = new LatLng(53.198438, 56.843609);
        LatLng destination = new LatLng(53.198438, 56.843609);
        presenter.getRoute(origin, destination, googleMap);
        stubInteractor.getDirections(origin, destination).test()
                .assertSubscribed()
                .assertValue(latLngs -> latLngs.size() == 2)
                .assertNoErrors()
                .assertComplete()
                .dispose();
    }

    @Test
    public void whenCallGetDeviceLocation() {
        presenter.getDeviceLocation();
        stubInteractor.getDeviceLocation().test()
                .assertSubscribed()
                .assertValue(Objects::nonNull)
                .assertNoErrors()
                .assertComplete()
                .dispose();
    }

}