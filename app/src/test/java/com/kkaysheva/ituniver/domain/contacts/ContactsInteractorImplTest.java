package com.kkaysheva.ituniver.domain.contacts;

import android.content.Context;

import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.domain.ContactRepository;
import com.kkaysheva.ituniver.domain.model.Contact;
import com.kkaysheva.ituniver.domain.model.ContactInfo;
import com.kkaysheva.ituniver.domain.tools.ContactInfoRepositoryStubImpl;
import com.kkaysheva.ituniver.domain.tools.ContactRepositoryStubImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * ContactsInteractorImplTest
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public class ContactsInteractorImplTest {

    private ContactRepository contactRepository;

    private ContactInfoRepository contactInfoRepository;

    private ContactsInteractor interactor;

    @Inject
    private Context context;

    @Before
    public void beforeTest() {
        contactRepository = new ContactRepositoryStubImpl();
        contactInfoRepository = new ContactInfoRepositoryStubImpl();
        interactor = new ContactsInteractorImpl(context, contactInfoRepository, contactRepository);
    }

    @Test
    public void whenCallGetContactsThenReturnAllContacts() {
        ((ContactRepositoryStubImpl) contactRepository)
                .addContact(new Contact(1, "Name", "123", "uri"));
        assertThat(interactor.getContacts(), instanceOf(Single.class));
        assertThat(interactor.getContacts().blockingGet().size(), is(1));
        assertThat(interactor.getContacts().blockingGet().get(0).getId(), is(1));
        assertThat(interactor.getContacts().blockingGet().get(0).getName(), is("Name"));
        assertThat(interactor.getContacts().blockingGet().get(0).getNumber(), is("123"));
        assertThat(interactor.getContacts().blockingGet().get(0).getPhotoUri(), is("uri"));
    }

    @Test
    public void whenCallGetContactsFromEmptyRepositoryThenReturnEmptyList() {
        assertThat(interactor.getContacts(), instanceOf(Single.class));
        assertThat(interactor.getContacts().blockingGet().size(), is(0));
    }

    @Test
    public void whenCallGetContactsByNameThenReturnContactsByThisName() {
        ((ContactRepositoryStubImpl) contactRepository)
                .addContact(new Contact(1, "Name1", "123", "uri"));
        ((ContactRepositoryStubImpl) contactRepository)
                .addContact(new Contact(1, "Name2", "123", "uri"));
        ((ContactRepositoryStubImpl) contactRepository)
                .addContact(new Contact(1, "Name3", "123", "uri"));
        ((ContactRepositoryStubImpl) contactRepository)
                .addContact(new Contact(1, "Test1", "123", "uri"));
        assertThat(interactor.getContactsByName("Name"), instanceOf(Single.class));
        assertThat(interactor.getContactsByName("Name").blockingGet().size(), is(3));
        assertThat(interactor.getContactsByName("Name").blockingGet().get(0).getName(), is("Name1"));
        assertThat(interactor.getContactsByName("Name").blockingGet().get(1).getName(), is("Name2"));
        assertThat(interactor.getContactsByName("Name").blockingGet().get(2).getName(), is("Name3"));
    }

    @Test
    public void whenCallGetContactsByNameWhichNotExistsThenReturnEmptyList() {
        assertThat(interactor.getContactsByName("Name"), instanceOf(Single.class));
        assertThat(interactor.getContactsByName("Name").blockingGet().size(), is(0));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void whenCallDeleteEmptyRowsThenEmptyRowsDeletedFromContactInfoRepository() {
        contactInfoRepository.insert(new ContactInfo(1, "1.2", "1.3", "address"));
        List<Contact> contacts = new ArrayList<>();
        assertThat(interactor.deleteEmptyRows(contacts), instanceOf(Completable.class));
        Disposable disposable = interactor.deleteEmptyRows(contacts)
                .subscribe(
                        () -> assertThat(contactInfoRepository.getAll().blockingGet().isEmpty(), is(true)),
                        Completable::error
                );
        disposable.dispose();
    }
}