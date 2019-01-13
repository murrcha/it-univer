package com.kkaysheva.ituniver.domain.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kkaysheva.ituniver.database.ContactRepository;
import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.model.ContactFetcher;
import com.kkaysheva.ituniver.model.ContactInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * ContactsInteractor
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public final class ContactsInteractor {

    private static final String TAG = ContactsInteractor.class.getSimpleName();

    @NonNull
    private final Context context;

    @NonNull
    private final ContactRepository repository;

    @Inject
    public ContactsInteractor(@NonNull Context context, @NonNull ContactRepository repository) {
        this.context = context;
        this.repository = repository;
    }

    public Single<List<Contact>> getContacts() {
        return ContactFetcher.getContacts(context);
    }

    public Single<List<Contact>> getContactsByName(String name) {
        return ContactFetcher.getContactsByName(name, context);
    }

    public Completable checkForDelete(List<Contact> contacts) {
        return repository.getAll()
                .map(contactInfoList -> getEmptyIdsFromContactsInfo(contacts, contactInfoList))
                .filter(ids -> !ids.isEmpty())
                .flatMapCompletable(this::deleteEmptyIds);
    }

    private List<Integer> getEmptyIdsFromContactsInfo(List<Contact> contacts, List<ContactInfo> contactInfoList) {
        List<Integer> emptyIds = new ArrayList<>();
        for (ContactInfo contactInfo : contactInfoList) {
            boolean result = false;
            for (Contact contact : contacts) {
                if (contactInfo.getId() == contact.getId()) {
                    result = true;
                    break;
                }
            }
            if (!result) {
                emptyIds.add(contactInfo.getId());
                Log.d(TAG, "getEmptyIdsFromContactsInfo: add empty id");
            }
        }
        return emptyIds;
    }

    private Completable deleteEmptyIds(List<Integer> emptyIds) {
        return Completable.fromAction(() -> {
            for (Integer id : emptyIds) {
                repository.delete(new ContactInfo(id, null, null, null));
                Log.d(TAG, "deleteEmptyIds: delete empty id");
            }
        });
    }
}
