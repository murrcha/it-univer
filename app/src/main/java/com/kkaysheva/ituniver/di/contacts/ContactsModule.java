package com.kkaysheva.ituniver.di.contacts;

import com.kkaysheva.ituniver.domain.ContactInfoRepository;
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

    @FragmentScope
    @Provides
    public ContactInfoRepository provideContactInfoRepository(ContactInfoRepositoryRoom repository) {
        return repository;
    }

    @FragmentScope
    @Provides
    public ContactsInteractor provideContactInteractor(ContactsInteractorImpl interactor) {
        return interactor;
    }
}
