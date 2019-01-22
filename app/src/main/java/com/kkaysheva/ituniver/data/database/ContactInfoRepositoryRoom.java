package com.kkaysheva.ituniver.data.database;

import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.domain.model.ContactInfo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * ContactInfoRepositoryRoom
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public final class ContactInfoRepositoryRoom implements ContactInfoRepository {

    @NonNull
    private final ContactDao contactDao;

    @Inject
    public ContactInfoRepositoryRoom(@NonNull ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @NonNull
    @Override
    public Single<List<ContactInfo>> getAll() {
        return contactDao.getAll();
    }

    @NonNull
    @Override
    public Maybe<ContactInfo> getById(@NonNull Long id) {
        return contactDao.getContactInfoById(id);
    }

    @NonNull
    @Override
    public Completable update(@NonNull ContactInfo contactInfo) {
        return Completable.fromAction(() -> contactDao.updateContactInfo(contactInfo));
    }

    @NonNull
    @Override
    public Completable insert(@NonNull ContactInfo contactInfo) {
        return Completable.fromAction(() -> contactDao.insertContactInfo(contactInfo));
    }

    @Override
    public void delete(@NonNull ContactInfo contactInfo) {
        contactDao.deleteContactInfo(contactInfo);
    }

    @NonNull
    @Override
    public Completable deleteAll() {
        return Completable.fromAction(contactDao::deleteAll);
    }
}
