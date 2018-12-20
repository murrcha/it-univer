package com.kkaysheva.ituniver.di.contacts;

import android.content.Context;

import com.kkaysheva.ituniver.di.scope.FragmentScope;
import com.kkaysheva.ituniver.presentation.contacts.ContactsPresenter;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Router;

/**
 * ContactsModule
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@Module
public class ContactsModule {

    @Provides
    @FragmentScope
    public ContactsPresenter getContactsPresenter(Router router, Context context) {
        return new ContactsPresenter(router, context);
    }
}
