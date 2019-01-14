package com.kkaysheva.ituniver.domain.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kkaysheva.ituniver.data.database.ContactInfoRepository;
import com.kkaysheva.ituniver.data.provider.ContactFetcher;
import com.kkaysheva.ituniver.domain.model.Contact;
import com.kkaysheva.ituniver.domain.model.ContactInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * ContactsInteractorImpl
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public final class ContactsInteractorImpl implements ContactsInteractor {

    private static final String TAG = ContactsInteractorImpl.class.getSimpleName();

    @NonNull
    private final Context context;

    @NonNull
    private final ContactInfoRepository repository;

    @Inject
    public ContactsInteractorImpl(@NonNull Context context, @NonNull ContactInfoRepository repository) {
        this.context = context;
        this.repository = repository;
    }

    @NonNull
    @Override
    public Single<List<Contact>> getContacts() {
        return ContactFetcher.getContacts(context);
    }

    @NonNull
    @Override
    public Single<List<Contact>> getContactsByName(@NonNull String name) {
        return ContactFetcher.getContactsByName(name, context);
    }

    @NonNull
    @Override
    public Completable deleteEmptyRows(@NonNull List<Contact> contacts) {
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
