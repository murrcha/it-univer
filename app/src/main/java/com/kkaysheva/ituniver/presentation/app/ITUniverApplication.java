package com.kkaysheva.ituniver.presentation.app;

import android.app.Application;
import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.di.app.ApplicationComponent;
import com.kkaysheva.ituniver.di.app.ApplicationModule;
import com.kkaysheva.ituniver.di.app.DaggerApplicationComponent;
import com.kkaysheva.ituniver.di.app.DatabaseModule;
import com.kkaysheva.ituniver.di.app.NavigationModule;
import com.kkaysheva.ituniver.di.app.NetworkModule;

/**
 * ITUniverApplication
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public final class ITUniverApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDependencies();
    }

    private void initDependencies() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .navigationModule(new NavigationModule())
                .databaseModule(new DatabaseModule())
                .networkModule(new NetworkModule())
                .build();
    }

    @NonNull
    public ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null) {
            initDependencies();
        }
        return applicationComponent;
    }
}
