package com.kkaysheva.ituniver.di.map;

import com.kkaysheva.ituniver.di.scope.FragmentScope;
import com.kkaysheva.ituniver.presentation.map.ContactMapFragment;

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
}
