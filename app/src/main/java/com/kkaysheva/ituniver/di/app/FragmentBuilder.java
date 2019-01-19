package com.kkaysheva.ituniver.di.app;

import com.kkaysheva.ituniver.di.contact.ContactModule;
import com.kkaysheva.ituniver.di.contacts.ContactsModule;
import com.kkaysheva.ituniver.di.map.MapModule;
import com.kkaysheva.ituniver.presentation.contact.ContactFragment;
import com.kkaysheva.ituniver.presentation.contacts.ContactsFragment;
import com.kkaysheva.ituniver.presentation.map.contact.ContactMapFragment;
import com.kkaysheva.ituniver.presentation.map.contacts.ContactsMapFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface FragmentBuilder {

    @ContributesAndroidInjector(modules = ContactsModule.class)
    ContactsFragment bindContactsFragment();

    @ContributesAndroidInjector(modules = ContactModule.class)
    ContactFragment bindContactFragment();

    @ContributesAndroidInjector(modules = MapModule.class)
    ContactsMapFragment bindContactsMapFragment();

    @ContributesAndroidInjector(modules = MapModule.class)
    ContactMapFragment bindContactMapFragment();
}
