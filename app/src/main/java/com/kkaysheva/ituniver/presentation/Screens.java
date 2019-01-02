package com.kkaysheva.ituniver.presentation;

import android.support.v4.app.Fragment;

import com.kkaysheva.ituniver.presentation.contact.ContactFragment;
import com.kkaysheva.ituniver.presentation.contacts.ContactsFragment;
import com.kkaysheva.ituniver.presentation.map.ContactMapFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

/**
 * Screens
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public final class Screens {

    public static final class ContactsScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            return ContactsFragment.newInstance();
        }
    }

    public static final class ContactScreen extends SupportAppScreen {

        private final int contactId;

        public ContactScreen(int contactId) {
            this.contactId = contactId;
        }

        @Override
        public Fragment getFragment() {
            return ContactFragment.newInstance(contactId);
        }
    }

    public static final class MapScreen extends SupportAppScreen {

        private final int contactId;

        public MapScreen(int contactId) {
            this.contactId = contactId;
        }

        @Override
        public Fragment getFragment() {
            return ContactMapFragment.newInstance(contactId);
        }
    }
}
