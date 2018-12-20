package com.kkaysheva.ituniver.di.contact;

import android.content.Context;

import com.kkaysheva.ituniver.di.scope.FragmentScope;
import com.kkaysheva.ituniver.presentation.contact.ContactPresenter;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Router;

/**
 * ContactModule
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@Module
public class ContactModule {

    @Provides
    @FragmentScope
    public ContactPresenter getContactPresenter(Router router, Context context) {
        return new ContactPresenter(router, context);
    }
}
