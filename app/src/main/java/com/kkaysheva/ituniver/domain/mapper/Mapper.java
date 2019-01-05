package com.kkaysheva.ituniver.domain.mapper;

import android.support.annotation.NonNull;

/**
 * Mapper
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public interface Mapper<From, To> {

    @NonNull
    To map(@NonNull From object);
}
