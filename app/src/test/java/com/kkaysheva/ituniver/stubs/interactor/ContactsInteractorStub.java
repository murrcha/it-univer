package com.kkaysheva.ituniver.stubs.interactor;


import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.contacts.ContactsInteractor;
import com.kkaysheva.ituniver.domain.model.Contact;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * ContactInteractorStub
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class ContactsInteractorStub implements ContactsInteractor {

    @NonNull
    @Override
    public Single<List<Contact>> getContacts() {
        return Single.fromCallable(() ->
                Arrays.asList(
                        new Contact(1, "", "", ""),
                        new Contact(2, "", "", "")
                )
        );
    }

    @NonNull
    @Override
    public Single<List<Contact>> getContactsByName(@NonNull String name) {
        return Single.fromCallable(() ->
                Arrays.asList(
                        new Contact(1, "test", "", ""),
                        new Contact(2, "test", "", "")
                )
        );
    }

    @NonNull
    @Override
    public Completable deleteEmptyRows(@NonNull List<Contact> contacts) {
        return Completable.fromAction(Completable::complete);
    }
}
