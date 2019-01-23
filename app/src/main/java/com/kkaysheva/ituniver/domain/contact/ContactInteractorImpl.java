package com.kkaysheva.ituniver.domain.contact;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.domain.ContactRepository;
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
    private final ContactInfoRepository contactInfoRepository;

    @NonNull
    private final ContactRepository contactRepository;

    @Inject
    public ContactInteractorImpl(@NonNull Context context,
                                 @NonNull ContactInfoRepository contactInfoRepository,
                                 @NonNull ContactRepository contactRepository) {
        this.context = context;
        this.contactInfoRepository = contactInfoRepository;
        this.contactRepository = contactRepository;
    }

    @NonNull
    @Override
    public Single<Contact> getContactById(int contactId) {
        return contactRepository.getContactById(contactId, context);
    }

    @NonNull
    @Override
    public Maybe<ContactInfo> getContactInfoById(int contactId) {
        return contactInfoRepository.getById((long) contactId);
    }
}
