package com.kkaysheva.ituniver.domain.contacts;

import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.model.Contact;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * ContactsInteractor
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public interface ContactsInteractor {

    @NonNull
    Single<List<Contact>> getContacts();

    @NonNull
    Single<List<Contact>> getContactsByName(@NonNull String name);

    @NonNull
    Completable deleteEmptyRows(@NonNull List<Contact> contacts);
}
