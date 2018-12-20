package com.kkaysheva.ituniver.di.contact;

import com.kkaysheva.ituniver.di.scope.FragmentScope;
import com.kkaysheva.ituniver.presentation.contact.ContactFragment;

import dagger.Subcomponent;

/**
 * ContactComponent
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@FragmentScope
@Subcomponent (modules = ContactModule.class)
public interface ContactComponent {
    void inject(ContactFragment contactFragment);
}
