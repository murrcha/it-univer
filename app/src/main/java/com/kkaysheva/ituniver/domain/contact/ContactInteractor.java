package com.kkaysheva.ituniver.domain.contact;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.model.ContactFetcher;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * ContactInteractor
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public class ContactInteractor {

    @NonNull
    private final Context context;

    @Inject
    public ContactInteractor(@NonNull Context context) {
        this.context = context;
    }

    public Single<Contact> getContactById(int contactId) {
        return ContactFetcher.getContactById(contactId, context);
    }
}
