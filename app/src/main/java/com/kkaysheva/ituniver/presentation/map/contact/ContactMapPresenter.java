package com.kkaysheva.ituniver.presentation.map.contact;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.domain.map.MapInteractor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ContactMapPresenter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@InjectViewState
public final class ContactMapPresenter extends MvpPresenter<ContactMapView> {

    private static final String TAG = ContactMapPresenter.class.getSimpleName();

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @NonNull
    private final MapInteractor interactor;

    @Inject
    public ContactMapPresenter(@NonNull MapInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    public void configureMap(GoogleMap map) {
        getViewState().configureMap(map);
    }

    public void addMarker(LatLng latLng, GoogleMap map) {
        getViewState().addMarker(latLng, map);
    }

    @SuppressLint("CheckResult")
    public void getAddress(int contactId, LatLng latLng) {
        //noinspection ResultOfMethodCallIgnored
        interactor.getAddress(latLng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(
                        address -> {
                            getViewState().showAddress(address);
                            saveAddress(contactId, latLng, address);
                        },
                        throwable -> {
                            getViewState().showError(throwable.getMessage());
                            Log.e(TAG, "getAddress: error", throwable);
                        }
                );
    }

    @SuppressLint("CheckResult")
    public void getLocationById(int contactId, GoogleMap map) {
        //noinspection ResultOfMethodCallIgnored
        interactor.getLocationById(contactId)
                .doOnSubscribe(compositeDisposable::add)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        location -> {
                            getViewState().addMarker(location, map);
                            getViewState().showMarker(location, map);
                            Log.d(TAG, "getLocationById: " + location.toString());
                        },
                        throwable -> Log.e(TAG, "getLocationById: error", throwable),
                        () -> Log.d(TAG, "getLocationById: no location")
                );
    }

    @SuppressLint("CheckResult")
    private void saveAddress(int contactId, LatLng latLng, String address) {
        //noinspection ResultOfMethodCallIgnored
        interactor.saveAddress(contactId, latLng, address)
                .doOnSubscribe(compositeDisposable::add)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.d(TAG, "saveAddress: saved"),
                        throwable -> Log.e(TAG, "saveAddress: error", throwable)
                );
    }

    @SuppressLint("CheckResult")
    public void getDeviceLocation() {
        //noinspection ResultOfMethodCallIgnored
        interactor.getDeviceLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(
                        location -> getViewState().saveDeviceLocation(location),
                        throwable -> getViewState().showError(throwable.getMessage()),
                        () -> getViewState().showError("No location")
                );
    }
}
