package com.kkaysheva.ituniver.data.database;

import android.support.annotation.NonNull;

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
    private final AppDatabase appDatabase;

    @Inject
    public ContactInfoRepositoryRoom(@NonNull AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @NonNull
    @Override
    public Single<List<ContactInfo>> getAll() {
        return appDatabase.getContactDao().getAll();
    }

    @NonNull
    @Override
    public Maybe<ContactInfo> getById(@NonNull Long id) {
        return appDatabase.getContactDao().getContactInfoById(id);
    }

    @NonNull
    @Override
    public Completable update(@NonNull ContactInfo contactInfo) {
        return Completable.fromAction(() -> appDatabase.getContactDao().updateContactInfo(contactInfo));
    }

    @NonNull
    @Override
    public Completable insert(@NonNull ContactInfo contactInfo) {
        return Completable.fromAction(() -> appDatabase.getContactDao().insertContactInfo(contactInfo));
    }

    @Override
    public void delete(@NonNull ContactInfo contactInfo) {
        appDatabase.getContactDao().deleteContactInfo(contactInfo);
    }

    @NonNull
    @Override
    public Completable deleteAll() {
        return Completable.fromAction(() -> appDatabase.getContactDao().deleteAll());
    }
}
