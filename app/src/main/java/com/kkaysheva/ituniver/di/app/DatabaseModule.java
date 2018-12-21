package com.kkaysheva.ituniver.di.app;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.kkaysheva.ituniver.BuildConfig;
import com.kkaysheva.ituniver.database.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class DatabaseModule {

    @Singleton
    @Provides
    public AppDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, BuildConfig.DATA_BASE_NAME)
                .build();
    }
}
