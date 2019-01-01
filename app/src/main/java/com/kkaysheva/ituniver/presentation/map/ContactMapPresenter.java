package com.kkaysheva.ituniver.presentation.map;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

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
    public void getAddress(LatLng latLng) {
        interactor.getAddress(latLng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(
                        s -> getViewState().showAddress(s),
                        throwable -> getViewState().showErrorLog(throwable)
                );
    }
}
