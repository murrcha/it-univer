package com.kkaysheva.ituniver.stubs.interactor;

import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.contact.ContactInteractor;
import com.kkaysheva.ituniver.domain.model.Contact;
import com.kkaysheva.ituniver.domain.model.ContactInfo;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * ContactInteractorStub
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class ContactInteractorStub implements ContactInteractor {

    @NonNull
    @Override
    public Single<Contact> getContactById(int contactId) {
        return Single.fromCallable(() -> {
            if (contactId > 0) {
                return new Contact(1, "test", "123", "test");
            } else {
                return null;
            }
        });
    }

    @NonNull
    @Override
    public Maybe<ContactInfo> getContactInfoById(int contactId) {
        return Maybe.fromCallable(() -> {
            if (contactId > 0) {
                return new ContactInfo(1, "", "", "");
            } else {
                return null;
            }
        });
    }
}
