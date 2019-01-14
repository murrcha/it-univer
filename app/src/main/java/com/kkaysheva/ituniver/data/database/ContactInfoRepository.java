package com.kkaysheva.ituniver.data.database;

import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.model.ContactInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * ContactInfoRepository
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public interface ContactInfoRepository {

    @NonNull
    Single<List<ContactInfo>> getAll();

    @NonNull
    Maybe<ContactInfo> getById(@NonNull Long id);

    @NonNull
    Completable insert(@NonNull ContactInfo contactInfo);

    @NonNull
    Completable update(@NonNull ContactInfo contactInfo);

    void delete(@NonNull ContactInfo contactInfo);

    @NonNull
    Completable deleteAll();
}
