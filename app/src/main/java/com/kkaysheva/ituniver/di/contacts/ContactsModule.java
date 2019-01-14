package com.kkaysheva.ituniver.di.contacts;

import com.kkaysheva.ituniver.data.database.ContactInfoRepository;
import com.kkaysheva.ituniver.data.database.ContactInfoRepositoryRoom;
import com.kkaysheva.ituniver.di.scope.FragmentScope;
import com.kkaysheva.ituniver.domain.contacts.ContactsInteractor;
import com.kkaysheva.ituniver.domain.contacts.ContactsInteractorImpl;

import dagger.Module;
import dagger.Provides;

/**
 * ContactsModule
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@Module
public final class ContactsModule {

    @Provides
    @FragmentScope
    public ContactInfoRepository provideContactInfoRepository(ContactInfoRepositoryRoom repository) {
        return repository;
    }

    @Provides
    @FragmentScope
    public ContactsInteractor provideContactInteractor(ContactsInteractorImpl interactor) {
        return interactor;
    }
}
