package com.kkaysheva.ituniver.presentation.map.contacts;

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
 * ContactsMapPresenter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
@InjectViewState
public final class ContactsMapPresenter extends MvpPresenter<ContactsMapView> {

    private static final String TAG = ContactsMapPresenter.class.getSimpleName();

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @NonNull
    private final MapInteractor interactor;

    @Inject
    public ContactsMapPresenter(@NonNull MapInteractor interactor) {
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    public void getLocationForAll(GoogleMap map) {
        interactor.getLocations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(
                        locations -> {
                            getViewState().addAllMarkers(locations, map);
                            getViewState().showAllMarkers(locations, map);
                        },
                        throwable -> Log.e(TAG, "getLocationForAll: error", throwable)
                );
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    public void getRoute(LatLng origin, LatLng destination, GoogleMap map) {
        interactor.getDirections(origin, destination)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(
                        locations -> getViewState().showRoute(locations, map),
                        throwable -> {
                            Log.e(TAG, "getRoute: error", throwable);
                            getViewState().showError(throwable.getMessage());
                        }
                );
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    public void getDeviceLocation() {
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
