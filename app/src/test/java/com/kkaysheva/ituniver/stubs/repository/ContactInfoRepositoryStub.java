package com.kkaysheva.ituniver.stubs.repository;

import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.domain.model.ContactInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * ContactInfoRepositoryStub
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class ContactInfoRepositoryStub implements ContactInfoRepository {

    private List<ContactInfo> repository = new ArrayList<>();

    @NonNull
    @Override
    public Single<List<ContactInfo>> getAll() {
        return Single.fromCallable(() -> repository);
    }

    @NonNull
    @Override
    public Maybe<ContactInfo> getById(@NonNull Long id) {
        return Maybe.fromCallable(() -> {
            ContactInfo result = null;
            for (ContactInfo contactInfo : repository) {
                if (contactInfo.getId() == id) {
                    result = contactInfo;
                    break;
                }
            }
            return result;
        });
    }

    @NonNull
    @Override
    public Completable insert(@NonNull ContactInfo contactInfo) {
        return Completable.fromAction(() -> repository.add(contactInfo));
    }

    @NonNull
    @Override
    public Completable update(@NonNull ContactInfo contactInfo) {
        return Completable.fromAction(() -> {});
    }

    @Override
    public void delete(@NonNull ContactInfo contactInfo) {
        for (int index = 0; index < repository.size(); index++) {
            if (repository.get(index).getId() == contactInfo.getId()) {
                repository.remove(index);
                break;
            }
        }
    }

    @NonNull
    @Override
    public Completable deleteAll() {
        return Completable.fromAction(() -> repository.clear());
    }
}
