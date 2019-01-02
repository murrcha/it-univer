package com.kkaysheva.ituniver.presentation.map;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.domain.map.ContactMapInteractor;

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
    private final ContactMapInteractor interactor;

    @Inject
    public ContactMapPresenter(@NonNull ContactMapInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    public void configureMap() {
        getViewState().configureMap();
    }

    public void addMarker(LatLng latLng) {
        getViewState().addMarker(latLng);
    }

    @SuppressLint("CheckResult")
    public void getAddress(int contactId, LatLng latLng) {
        interactor.getAddress(latLng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(
                        address -> {
                            getViewState().showAddress(address);
                            saveAddress(contactId, latLng, address);
                        },
                        throwable -> Log.e(TAG, "getAddress: error", throwable)
                );
    }

    @SuppressLint("CheckResult")
    public void getLocationById(int contactId) {
        interactor.getLatLngById(contactId)
                .doOnSubscribe(compositeDisposable::add)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        latLng -> {
                            getViewState().addMarker(latLng);
                            getViewState().showMarker(latLng);
                            Log.d(TAG, "getLocationById: " + latLng.toString());
                        },
                        throwable -> Log.e(TAG, "getLocationById: error", throwable)
                );
    }

    @SuppressLint("CheckResult")
    private void saveAddress(int contactId, LatLng latLng, String address) {
        interactor.saveAddress(contactId, latLng, address)
                .doOnSubscribe(compositeDisposable::add)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.d(TAG, "saveAddress: saved"),
                        throwable -> Log.e(TAG, "saveAddress: error", throwable)
                );
    }
}
