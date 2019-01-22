package com.kkaysheva.ituniver.di.contacts;

import com.kkaysheva.ituniver.di.scope.FragmentScope;
import com.kkaysheva.ituniver.presentation.contacts.ContactsFragment;

import dagger.Subcomponent;

/**
 * ContactsComponent
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@FragmentScope
@Subcomponent (modules = ContactsModule.class)
public interface ContactsComponent {
    void inject(ContactsFragment contactsFragment);
}
