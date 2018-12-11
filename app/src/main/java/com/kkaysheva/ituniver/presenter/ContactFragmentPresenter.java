package com.kkaysheva.ituniver.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.App;
import com.kkaysheva.ituniver.Screens;
import com.kkaysheva.ituniver.view.ContactFragmentView;
import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.model.ContactFetcher;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

/**
 * ContactFragmentPresenter
 *
 * @author Ksenya Kaysheva  (murrcha@me.com)
 * @since 11.2018
 */

@InjectViewState
public final class ContactFragmentPresenter extends MvpPresenter<ContactFragmentView> {

    private static final String TAG = ContactFragmentPresenter.class.getSimpleName();

    private final Router router;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final Observer<Contact> observer = new Observer<Contact>() {
        @Override
        public void onSubscribe(Disposable d) {
            getViewState().showProgress(true);
            compositeDisposable.add(d);
        }

        @Override
        public void onNext(Contact contact) {
            getViewState().loadContact(contact);
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

    public ContactFragmentPresenter() {
        this.router = App.instance.getRouter();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    public void fetchContact(int contactId) {
        Observable.fromCallable(ContactFetcher.getContactById(contactId, App.getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void onForwardCommandClick() {
        router.navigateTo(new Screens.MapScreen());
    }
}
