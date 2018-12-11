package com.kkaysheva.ituniver.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.App;
import com.kkaysheva.ituniver.Screens;
import com.kkaysheva.ituniver.view.ContactsFragmentView;
import com.kkaysheva.ituniver.model.ContactFetcher;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ContactsFragmentPresenter() {
        router = App.instance.getRouter();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    public void fetchContacts() {
        Single.fromCallable(ContactFetcher.getContacts(App.getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    getViewState().showProgress(true);
                    compositeDisposable.add(disposable);
                })
                .doOnSuccess(contacts -> {
                    getViewState().loadContacts(contacts);
                    getViewState().showProgress(false);
                })
                .doOnError(throwable -> Log.e(TAG, "onError: ", throwable))
                .subscribe();
    }

    public void fetchContactsByName(String name) {
        getViewState().saveQuery(name);
        Single.fromCallable(ContactFetcher.getContactsByName(name, App.getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    getViewState().showProgress(true);
                    compositeDisposable.add(disposable);
                })
                .doOnSuccess(contacts -> {
                    getViewState().loadContacts(contacts);
                    getViewState().showProgress(false);
                })
                .doOnError(throwable -> Log.e(TAG, "onError: ", throwable))
                .subscribe();
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
