package com.kkaysheva.ituniver.domain.tools;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.ContactRepository;
import com.kkaysheva.ituniver.domain.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;

/**
 * ContactRepositoryStubImpl
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class ContactRepositoryStubImpl implements ContactRepository {

    private List<Contact> repository = new ArrayList<>();

    @NonNull
    @Override
    public Single<List<Contact>> getContacts(@NonNull Context context) {
        return Single.just(repository);
    }

    @NonNull
    @Override
    public Single<List<Contact>> getContactsByName(@NonNull String searchName, @NonNull Context context) {
        List<Contact> result = new ArrayList<>();
        for (Contact contact : repository) {
            if (contact.getName().contains(searchName)) {
                result.add(contact);
            }
        }
        return Single.just(result);
    }

    @NonNull
    @Override
    public Single<Contact> getContactById(int contactId, @NonNull Context context) {
        Contact result = null;
        for (Contact contact : repository) {
            if (contact.getId() == contactId) {
                result = contact;
                break;
            }
        }
        return Single.just(Objects.requireNonNull(result));
    }

    public void addContact(@NonNull Contact contact) {
        repository.add(contact);
    }
}
