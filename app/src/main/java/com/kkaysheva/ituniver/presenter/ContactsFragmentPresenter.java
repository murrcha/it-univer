package com.kkaysheva.ituniver.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.App;
import com.kkaysheva.ituniver.Screens;
import com.kkaysheva.ituniver.view.ContactsFragmentView;
import com.kkaysheva.ituniver.model.ContactFetcher;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

/**
 * ContactsFragmentPresenter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */

@InjectViewState
public final class ContactsFragmentPresenter extends MvpPresenter<ContactsFragmentView> {

    private static final String TAG = ContactsFragmentPresenter.class.getSimpleName();

    private final Router router;

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ContactsFragmentPresenter() {
        router = App.instance.getRouter();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @SuppressLint("CheckResult")
    public void fetchContacts() {
        ContactFetcher.getContacts(App.getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    getViewState().showProgress(true);
                    compositeDisposable.add(disposable);
                })
                .subscribe(
                        contacts -> {
                            getViewState().loadContacts(contacts);
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
        ContactFetcher.getContactsByName(name, App.getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    getViewState().showProgress(true);
                    compositeDisposable.add(disposable);
                })
                .subscribe(
                        contacts -> {
                            getViewState().loadContacts(contacts);
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
