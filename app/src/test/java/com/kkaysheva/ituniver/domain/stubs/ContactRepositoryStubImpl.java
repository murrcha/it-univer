package com.kkaysheva.ituniver.domain.stubs;

import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.ContactRepository;
import com.kkaysheva.ituniver.domain.model.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public Single<List<Contact>> getContacts() {
        return Single.fromCallable(() -> Collections.unmodifiableList(repository));
    }

    @NonNull
    @Override
    public Single<List<Contact>> getContactsByName(@NonNull String searchName) {
        return Single.fromCallable(() -> {
            List<Contact> result = new ArrayList<>();
            for (Contact contact : repository) {
                if (contact.getName().contains(searchName)) {
                    result.add(contact);
                }
            }
            return result;
        });
    }

    @NonNull
    @Override
    public Single<Contact> getContactById(int contactId) {
        return Single.fromCallable(() -> {
            Contact result = null;
            for (Contact contact : repository) {
                if (contact.getId() == contactId) {
                    result = contact;
                    break;
                }
            }
            return result;
        });
    }

    public void addContacts(@NonNull List<Contact> contacts) {
        repository.addAll(contacts);
    }

    public void addContact(@NonNull Contact contact) {
        repository.add(contact);
    }
}
