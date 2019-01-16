package com.kkaysheva.ituniver.domain.contact;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.data.provider.contacts.ContactFetcher;
import com.kkaysheva.ituniver.domain.model.Contact;
import com.kkaysheva.ituniver.domain.model.ContactInfo;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * ContactInteractorImpl
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public final class ContactInteractorImpl implements ContactInteractor {

    @NonNull
    private final Context context;

    @NonNull
    private final ContactInfoRepository repository;

    @Inject
    public ContactInteractorImpl(@NonNull Context context, @NonNull ContactInfoRepository repository) {
        this.context = context;
        this.repository = repository;
    }

    @NonNull
    @Override
    public Single<Contact> getContactById(int contactId) {
        return ContactFetcher.getContactById(contactId, context);
    }

    @NonNull
    @Override
    public Maybe<ContactInfo> getContactInfoById(int contactId) {
        return repository.getById((long) contactId);
    }
}
