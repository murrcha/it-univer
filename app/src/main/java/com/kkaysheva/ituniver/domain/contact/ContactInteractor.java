package com.kkaysheva.ituniver.domain.contact;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.database.ContactRepository;
import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.model.ContactFetcher;
import com.kkaysheva.ituniver.model.ContactInfo;

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

    @NonNull
    private final ContactRepository repository;

    @Inject
    public ContactInteractor(@NonNull Context context, @NonNull ContactRepository repository) {
        this.context = context;
        this.repository = repository;
    }

    public Single<Contact> getContactById(int contactId) {
        return ContactFetcher.getContactById(contactId, context);
    }

    public Single<ContactInfo> getContactInfoById(int contactId) {
        return repository.getById((long) contactId);
    }
}
