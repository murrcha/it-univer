package com.kkaysheva.ituniver.domain.contacts;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.domain.ContactRepository;
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
    private final ContactInfoRepository contactInfoRepository;

    @NonNull
    private final ContactRepository contactRepository;

    @Inject
    public ContactsInteractorImpl(@NonNull ContactInfoRepository contactInfoRepository,
                                  @NonNull ContactRepository contactRepository) {
        this.contactInfoRepository = contactInfoRepository;
        this.contactRepository = contactRepository;
    }

    @NonNull
    @Override
    public Single<List<Contact>> getContacts() {
        return contactRepository.getContacts();
    }

    @NonNull
    @Override
    public Single<List<Contact>> getContactsByName(@NonNull String name) {
        return contactRepository.getContactsByName(name);
    }

    @NonNull
    @Override
    public Completable deleteEmptyRows(@NonNull List<Contact> contacts) {
        return contactInfoRepository.getAll()
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
                contactInfoRepository.delete(new ContactInfo(id, null, null, null));
                Log.d(TAG, "deleteEmptyIds: delete empty id");
            }
        });
    }
}
