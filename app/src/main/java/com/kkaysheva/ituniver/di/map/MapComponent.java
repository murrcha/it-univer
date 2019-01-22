package com.kkaysheva.ituniver.di.map;

import com.kkaysheva.ituniver.di.scope.FragmentScope;
import com.kkaysheva.ituniver.presentation.map.contact.ContactMapFragment;
import com.kkaysheva.ituniver.presentation.map.contacts.ContactsMapFragment;

import dagger.Subcomponent;

/**
 * MapComponent
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@FragmentScope
@Subcomponent(modules = {MapModule.class})
public interface MapComponent {
    void inject(ContactMapFragment fragment);
    void inject(ContactsMapFragment fragment);
}
