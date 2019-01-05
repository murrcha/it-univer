package com.kkaysheva.ituniver.di.app;

import android.content.Context;

import com.kkaysheva.ituniver.app.AppDelegate;
import com.kkaysheva.ituniver.database.AppDatabase;
import com.kkaysheva.ituniver.di.contact.ContactComponent;
import com.kkaysheva.ituniver.di.contacts.ContactsComponent;
import com.kkaysheva.ituniver.di.main.MainComponent;
import com.kkaysheva.ituniver.di.main.MainModule;
import com.kkaysheva.ituniver.di.map.MapComponent;
import com.kkaysheva.ituniver.network.GeoCodeApi;
import com.kkaysheva.ituniver.network.GoogleDirectionsApi;

import javax.inject.Singleton;

import dagger.Component;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

/**
 * AppComponent
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@Singleton
@Component(modules = {
        AppModule.class,
        NavigationModule.class,
        DatabaseModule.class,
        NetworkModule.class,
        MapperModule.class })
public interface AppComponent {

    Router provideRouter();

    NavigatorHolder provideNavigationHolder();

    Context provideApplicationContext();

    AppDatabase provideDatabase();

    GeoCodeApi provideGeoCodeApi();

    GoogleDirectionsApi provideGoogleDirections();

    MainComponent plusMainComponent(MainModule mainModule);

    ContactsComponent plusContactsComponent();

    ContactComponent plusContactComponent();

    MapComponent plusMapComponent();

    void inject(AppDelegate appDelegate);
}
