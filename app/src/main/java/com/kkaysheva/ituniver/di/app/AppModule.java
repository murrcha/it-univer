package com.kkaysheva.ituniver.di.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * AppModule
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@Module
public final class AppModule {

    @NonNull
    private final Application application;

    public AppModule(@NonNull Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return application;
    }
}
