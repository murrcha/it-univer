package com.kkaysheva.ituniver.presentation.contact;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.domain.contact.ContactInteractor;
import com.kkaysheva.ituniver.presentation.Screens;

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
    private final ContactInteractor interactor;

    @NonNull
    private final Router router;

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    ContactPresenter(@NonNull Router router, @NonNull ContactInteractor interactor) {
        this.router = router;
        this.interactor = interactor;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @SuppressLint("CheckResult")
    public void fetchContact(int contactId) {
        //noinspection ResultOfMethodCallIgnored
        interactor.getContactById(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    compositeDisposable.add(disposable);
                    getViewState().showProgress(true);
                })
                .subscribe(
                        contact -> {
                            getViewState().showContact(contact);
                            getViewState().showProgress(false);
                        },
                        throwable -> {
                            getViewState().showProgress(false);
                            Log.e(TAG, "fetchContact: ", throwable);
                        }
                );
    }


    @SuppressLint("CheckResult")
    public void fetchContactInfo(int contactId) {
        //noinspection ResultOfMethodCallIgnored
        interactor.getContactInfoById(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(
                        contactInfo -> getViewState().showContactInfo(contactInfo),
                        throwable -> Log.e(TAG, "fetchContactInfo: error", throwable)
                );
    }

    public void onForwardCommandClick(int contactId) {
        router.navigateTo(new Screens.ContactMapScreen(contactId));
    }
}
