package com.kkaysheva.ituniver.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.App;
import com.kkaysheva.ituniver.Screens;
import com.kkaysheva.ituniver.view.ContactFragmentView;
import com.kkaysheva.ituniver.model.ContactFetcher;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
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

    public ContactFragmentPresenter() {
        this.router = App.instance.getRouter();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    public void fetchContact(int contactId) {
        Single.fromCallable(ContactFetcher.getContactById(contactId, App.getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    compositeDisposable.add(disposable);
                    getViewState().showProgress(true);
                })
                .doOnSuccess(contact -> {
                    getViewState().loadContact(contact);
                    getViewState().showProgress(false);
                })
                .doOnError((throwable) -> Log.e(TAG, "fetchContact: ", throwable))
                .subscribe();
    }

    public void onForwardCommandClick() {
        router.navigateTo(new Screens.MapScreen());
    }
}
