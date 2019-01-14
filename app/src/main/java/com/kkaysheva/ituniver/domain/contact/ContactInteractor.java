package com.kkaysheva.ituniver.domain.contact;

import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.model.Contact;
import com.kkaysheva.ituniver.domain.model.ContactInfo;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * ContactInteractor
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public interface ContactInteractor {

    @NonNull
    Single<Contact> getContactById(int contactId);

    @NonNull
    Maybe<ContactInfo> getContactInfoById(int contactId);
}
