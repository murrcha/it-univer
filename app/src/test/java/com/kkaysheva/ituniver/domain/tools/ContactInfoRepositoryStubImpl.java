package com.kkaysheva.ituniver.domain.tools;

import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.domain.model.ContactInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * ContactInfoRepositoryStubImpl
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class ContactInfoRepositoryStubImpl implements ContactInfoRepository {

    private List<ContactInfo> repository = new ArrayList<>();

    @NonNull
    @Override
    public Single<List<ContactInfo>> getAll() {
        return Single.just(repository);
    }

    @NonNull
    @Override
    public Maybe<ContactInfo> getById(@NonNull Long id) {
        ContactInfo result = null;
        for (ContactInfo contactInfo : repository) {
            if (contactInfo.getId() == id) {
                result = contactInfo;
                break;
            }
        }
        return Maybe.just(Objects.requireNonNull(result));
    }

    @NonNull
    @Override
    public Completable insert(@NonNull ContactInfo contactInfo) {
        repository.add(contactInfo);
        return Completable.complete();
    }

    @NonNull
    @Override
    public Completable update(@NonNull ContactInfo contactInfo) {
        //no implementation
        return Completable.complete();
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
        repository.clear();
        return Completable.complete();
    }
}
