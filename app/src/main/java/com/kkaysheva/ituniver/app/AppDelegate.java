package com.kkaysheva.ituniver.app;

import android.app.Application;
import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.di.app.AppComponent;
import com.kkaysheva.ituniver.di.app.AppModule;
import com.kkaysheva.ituniver.di.app.DaggerAppComponent;
import com.kkaysheva.ituniver.di.app.NavigationModule;

/**
 * AppDelegate
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public final class AppDelegate extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDependencies();
    }

    private void initDependencies() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .navigationModule(new NavigationModule())
                .build();
    }

    @NonNull
    public AppComponent getAppComponent() {
        if (appComponent == null) {
            initDependencies();
        }
        return appComponent;
    }
}
