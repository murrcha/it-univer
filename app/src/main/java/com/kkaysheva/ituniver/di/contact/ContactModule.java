package com.kkaysheva.ituniver.di.contact;

import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.data.database.ContactInfoRepositoryRoom;
import com.kkaysheva.ituniver.di.scope.FragmentScope;
import com.kkaysheva.ituniver.domain.contact.ContactInteractor;
import com.kkaysheva.ituniver.domain.contact.ContactInteractorImpl;

import dagger.Module;
import dagger.Provides;

/**
 * ContactModule
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@Module
public final class ContactModule {

    @FragmentScope
    @Provides
    public ContactInfoRepository provideContactInfoRepository(ContactInfoRepositoryRoom repository) {
        return repository;
    }

    @FragmentScope
    @Provides
    public ContactInteractor provideContactInteractor(ContactInteractorImpl interactor) {
        return interactor;
    }
}
