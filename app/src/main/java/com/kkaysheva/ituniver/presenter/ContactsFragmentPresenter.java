package com.kkaysheva.ituniver.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.App;
import com.kkaysheva.ituniver.Screens;
import com.kkaysheva.ituniver.view.ContactsFragmentView;
import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.model.ContactFetcher;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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

    private final Observer<List<Contact>> observer = new Observer<List<Contact>>() {
        @Override
        public void onSubscribe(Disposable d) {
            getViewState().showProgress(true);
            compositeDisposable.add(d);
        }

        @Override
        public void onNext(List<Contact> contacts) {
            getViewState().loadContacts(contacts);
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError: ", e);
        }

        @Override
        public void onComplete() {
            getViewState().showProgress(false);
        }
    };

    public ContactsFragmentPresenter() {
        router = App.instance.getRouter();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    public void fetchContacts() {
        Observable.fromCallable(ContactFetcher.getContacts(App.getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void fetchContactsByName(String name) {
        getViewState().saveQuery(name);
        Observable.fromCallable(ContactFetcher.getContactsByName(name, App.getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
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
