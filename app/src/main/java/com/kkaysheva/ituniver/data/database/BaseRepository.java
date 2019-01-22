package com.kkaysheva.ituniver.data.database;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * BaseRepository
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public interface BaseRepository<Entity, Id> {

    @NonNull
    Single<List<Entity>> getAll();

    @NonNull
    Maybe<Entity> getById(@NonNull Id id);

    @NonNull
    Completable update(@NonNull Entity entity);

    @NonNull
    Completable insert(@NonNull Entity entity);

    void delete(@NonNull Entity entity);

    @NonNull
    Completable deleteAll();
}
