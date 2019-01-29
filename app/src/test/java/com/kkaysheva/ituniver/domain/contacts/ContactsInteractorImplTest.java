package com.kkaysheva.ituniver.domain.contacts;

import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.domain.ContactRepository;
import com.kkaysheva.ituniver.domain.model.Contact;
import com.kkaysheva.ituniver.domain.model.ContactInfo;
import com.kkaysheva.ituniver.stubs.repository.ContactInfoRepositoryStub;
import com.kkaysheva.ituniver.stubs.repository.ContactRepositoryStub;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.observers.TestObserver;

/**
 * ContactsInteractorImplTest
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class ContactsInteractorImplTest {

    private ContactRepository stubContactRepository;

    private ContactInfoRepository stubContactInfoRepository;

    private ContactsInteractor interactor;

    @Before
    public void before() {
        stubContactRepository = new ContactRepositoryStub();
        stubContactInfoRepository = new ContactInfoRepositoryStub();
        interactor = new ContactsInteractorImpl(stubContactInfoRepository, stubContactRepository);
    }

    @Test
    public void whenCallGetContactsThenReturnAllContacts() {
        List<Contact> contacts = Arrays.asList(
                new Contact(1, "Name1", "123", "uri"),
                new Contact(2, "Name2", "123", "uri")
        );
        ((ContactRepositoryStub) stubContactRepository).addContacts(contacts);
        TestObserver<List<Contact>> testObserver = new TestObserver<>();
        interactor.getContacts().subscribe(testObserver);
        //noinspection unchecked
        testObserver
                .assertSubscribed()
                .assertResult(contacts)
                .dispose();
    }

    @Test
    public void whenCallGetContactsFromEmptyRepositoryThenReturnEmptyList() {
        List<Contact> contacts = new ArrayList<>();
        TestObserver<List<Contact>> testObserver = new TestObserver<>();
        interactor.getContacts().subscribe(testObserver);
        //noinspection unchecked
        testObserver
                .assertSubscribed()
                .assertResult(contacts)
                .dispose();
    }

    @Test
    public void whenCallGetContactsByNameThenReturnContactsByThisName() {
        List<Contact> contacts = Arrays.asList(
                new Contact(1, "Name1", "123", "uri"),
                new Contact(2, "Name2", "123", "uri"),
                new Contact(3, "Test1", "123", "uri")
        );
        List<Contact> result = Arrays.asList(contacts.get(0), contacts.get(1));
        ((ContactRepositoryStub) stubContactRepository).addContacts(contacts);
        TestObserver<List<Contact>> testObserver = new TestObserver<>();
        interactor.getContactsByName("Name").subscribe(testObserver);
        //noinspection unchecked
        testObserver
                .assertSubscribed()
                .assertResult(result)
                .dispose();
    }

    @Test
    public void whenCallGetContactsByNameWhichNotExistsThenReturnEmptyList() {
        List<Contact> contacts = new ArrayList<>();
        TestObserver<List<Contact>> testObserver = new TestObserver<>();
        interactor.getContactsByName("Name").subscribe(testObserver);
        //noinspection unchecked
        testObserver
                .assertSubscribed()
                .assertResult(contacts)
                .dispose();
    }

    @Test
    public void whenCallDeleteEmptyRowsThenEmptyRowsDeletedFromContactInfoRepository() {
        TestObserver<Completable> completableTestObserver = new TestObserver<>();
        stubContactInfoRepository.insert(
                new ContactInfo(1, "1.2", "1.3", "address"))
                .subscribe(completableTestObserver);
        completableTestObserver
                .assertSubscribed()
                .assertComplete()
                .dispose();
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(1, "test", "123", "test"));
        contacts.add(new Contact(2, "test", "123", "test"));
        TestObserver<Completable> testObserver = new TestObserver<>();
        interactor.deleteEmptyRows(contacts).subscribe(testObserver);
        testObserver
                .assertSubscribed()
                .assertOf(TestObserver::onComplete)
                .dispose();
    }
}