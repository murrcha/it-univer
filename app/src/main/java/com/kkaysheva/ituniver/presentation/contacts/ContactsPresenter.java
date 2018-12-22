package com.kkaysheva.ituniver.presentation.contacts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.Screens;
import com.kkaysheva.ituniver.model.ContactFetcher;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

/**
 * ContactsPresenter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */

@InjectViewState
public final class ContactsPresenter extends MvpPresenter<ContactsView> {

    private static final String TAG = ContactsPresenter.class.getSimpleName();

    @NonNull
    private final Router router;

    @NonNull
    private final Context context;

    @io.reactivex.annotations.NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    ContactsPresenter(@NonNull Router router, @NonNull Context context) {
        this.router = router;
        this.context = context;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @SuppressLint("CheckResult")
    public void fetchContacts() {
        ContactFetcher.getContacts(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    getViewState().showProgress(true);
                    compositeDisposable.add(disposable);
                })
                .subscribe(
                        contacts -> {
                            getViewState().showContacts(contacts);
                            getViewState().showProgress(false);
                        },
                        throwable -> {
                            getViewState().showProgress(false);
                            Log.e(TAG, "onError: ", throwable);
                        }
                );
    }

    @SuppressLint("CheckResult")
    public void fetchContactsByName(String name) {
        getViewState().saveQuery(name);
        ContactFetcher.getContactsByName(name, context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    getViewState().showProgress(true);
                    compositeDisposable.add(disposable);
                })
                .subscribe(
                        contacts -> {
                            getViewState().showContacts(contacts);
                            getViewState().showProgress(false);
                        },
                        throwable -> {
                            getViewState().showProgress(false);
                            Log.e(TAG, "onError: ", throwable);
                        }
                );
    }

    public void onForwardCommandClick(int contactId) {
        router.navigateTo(new Screens.ContactScreen(contactId));
    }

    public void showMessage(int message) {
        getViewState().showMessage(message);
    }

    public void hideMessage() {
        getViewState().hideMessage();
    }
}