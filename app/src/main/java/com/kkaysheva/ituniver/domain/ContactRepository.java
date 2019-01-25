package com.kkaysheva.ituniver.domain;

import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.model.Contact;

import java.util.List;

import io.reactivex.Single;

/**
 * ContactRepository
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public interface ContactRepository {

    @NonNull
    Single<List<Contact>> getContacts();

    @NonNull
    Single<List<Contact>> getContactsByName(@NonNull String searchName);

    @NonNull
    Single<Contact> getContactById(int contactId);
}
