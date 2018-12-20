package com.kkaysheva.ituniver.presentation.contact;

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
 * ContactPresenter
 *
 * @author Ksenya Kaysheva  (murrcha@me.com)
 * @since 11.2018
 */

@InjectViewState
public final class ContactPresenter extends MvpPresenter<ContactView> {

    private static final String TAG = ContactPresenter.class.getSimpleName();

    @NonNull
    private final Context context;

    @NonNull
    private final Router router;

    @io.reactivex.annotations.NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public ContactPresenter(@android.support.annotation.NonNull Router router, @NonNull Context context) {
        this.router = router;
        this.context = context;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @SuppressLint("CheckResult")
    public void fetchContact(int contactId) {
        ContactFetcher.getContactById(contactId, context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    compositeDisposable.add(disposable);
                    getViewState().showProgress(true);
                })
                .subscribe(
                        contact -> {
                            getViewState().loadContact(contact);
                            getViewState().showProgress(false);
                        },
                        throwable -> {
                            getViewState().showProgress(false);
                            Log.e(TAG, "fetchContact: ", throwable);
                        }
                );
    }

    public void onForwardCommandClick() {
        router.navigateTo(new Screens.MapScreen());
    }
}
