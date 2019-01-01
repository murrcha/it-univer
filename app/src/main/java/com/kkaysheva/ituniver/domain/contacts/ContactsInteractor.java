package com.kkaysheva.ituniver.domain.contacts;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.model.ContactFetcher;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * ContactsInteractor
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public final class ContactsInteractor {

    @NonNull
    private final Context context;

    @Inject
    public ContactsInteractor(@NonNull Context context) {
        this.context = context;
    }

    public Single<List<Contact>> getContacts() {
        return ContactFetcher.getContacts(context);
    }

    public Single<List<Contact>> getContactsByName(String name) {
        return ContactFetcher.getContactsByName(name, context);
    }

}
