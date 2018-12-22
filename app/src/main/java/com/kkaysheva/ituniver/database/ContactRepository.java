package com.kkaysheva.ituniver.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kkaysheva.ituniver.model.ContactInfo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * ContactRepository
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public final class ContactRepository implements BaseRepository<ContactInfo, Long> {

    @NonNull
    private AppDatabase appDatabase;

    @Inject
    public ContactRepository(@NonNull AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @NonNull
    @Override
    public Single<List<ContactInfo>> getAll() {
        return appDatabase.getContactDaO().getAll();
    }

    @Nullable
    @Override
    public Single<ContactInfo> getById(@NonNull Long id) {
        return appDatabase.getContactDaO().getContactInfoById(id);
    }

    @NonNull
    @Override
    public Completable update(@NonNull ContactInfo contactInfo) {
        return Completable.fromAction(() -> appDatabase.getContactDaO().updateContactInfo(contactInfo));
    }

    @NonNull
    @Override
    public Completable insert(@NonNull ContactInfo contactInfo) {
        return Completable.fromAction(() -> appDatabase.getContactDaO().insertContactInfo(contactInfo));
    }

    @NonNull
    @Override
    public Completable delete(@NonNull ContactInfo contactInfo) {
        return Completable.fromAction(() -> appDatabase.getContactDaO().deleteContactInfo(contactInfo));
    }

    @NonNull
    @Override
    public Completable deleteAll() {
        return Completable.fromAction(() -> appDatabase.getContactDaO().deleteAll());
    }
}
