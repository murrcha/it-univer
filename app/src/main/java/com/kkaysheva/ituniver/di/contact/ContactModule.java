package com.kkaysheva.ituniver.di.contact;

import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.data.provider.contacts.ContactFetcher;
import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.data.database.ContactInfoRepositoryRoom;
import com.kkaysheva.ituniver.di.scope.FragmentScope;
import com.kkaysheva.ituniver.domain.ContactRepository;
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
    public ContactInfoRepository provideContactInfoRepository(@NonNull ContactInfoRepositoryRoom repository) {
        return repository;
    }

    @FragmentScope
    @Provides
    public ContactInteractor provideContactInteractor(@NonNull ContactInteractorImpl interactor) {
        return interactor;
    }

    @FragmentScope
    @Provides
    public ContactRepository provideContactRepository(@NonNull ContactFetcher repository) {
        return repository;
    }
}
