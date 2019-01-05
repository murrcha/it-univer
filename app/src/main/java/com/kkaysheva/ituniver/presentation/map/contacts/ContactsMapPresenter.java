package com.kkaysheva.ituniver.presentation.map.contacts;

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
    private final ContactMapInteractor interactor;

    @Inject
    public ContactsMapPresenter(@NonNull ContactMapInteractor interactor) {
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    public void getLocationForAll() {
        interactor.getLatLngAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(
                        locations -> {
                            getViewState().addAllMarkers(locations);
                            getViewState().showAllMarkers(locations);
                        },
                        throwable -> Log.e(TAG, "getLocationForAll: error", throwable)
                );
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    public void getRoute(LatLng origin, LatLng destination) {
        interactor.getDirections(origin, destination)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(
                        locations -> getViewState().showRoute(locations),
                        throwable -> {
                            Log.e(TAG, "getRoute: error", throwable);
                            getViewState().showError(throwable.getMessage());
                        }
                );
    }
}
