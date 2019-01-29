package com.kkaysheva.ituniver.presentation.map.contact;

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
 * ContactMapPresenterTest
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public class ContactMapPresenterTest {

    @Rule
    public final TestSchedulerRule testSchedulerRule = new TestSchedulerRule();

    private MapInteractor stubInteractor;

    private ContactMapPresenter presenter;

    @Mock
    private GoogleMap googleMap;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        stubInteractor = new MapInteractorStub();
        presenter = new ContactMapPresenter(stubInteractor);
    }

    @Test
    public void whenCallGetAddress() {
        LatLng latLng = new LatLng(53.198438, 56.843609);
        presenter.getAddress(1, latLng);
        stubInteractor.getAddress(latLng).test()
                .assertSubscribed()
                .assertValue(value -> value.equals("address"))
                .assertNoErrors()
                .assertComplete()
                .dispose();
    }

    @Test
    public void whenCallGetLocationById() {
        LatLng latLng = new LatLng(53.198438, 56.843609);
        presenter.getLocationById(1, googleMap);
        stubInteractor.getLocationById(1).test()
                .assertSubscribed()
                .assertValue(value -> value.equals(latLng))
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