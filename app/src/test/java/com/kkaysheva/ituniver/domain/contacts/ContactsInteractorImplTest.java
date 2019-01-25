package com.kkaysheva.ituniver.domain.contacts;

import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.domain.ContactRepository;
import com.kkaysheva.ituniver.domain.model.Contact;
import com.kkaysheva.ituniver.domain.model.ContactInfo;
import com.kkaysheva.ituniver.domain.stubs.ContactInfoRepositoryStubImpl;
import com.kkaysheva.ituniver.domain.stubs.ContactRepositoryStubImpl;

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
public class ContactsInteractorImplTest {

    private ContactRepository stubContactRepository;

    private ContactInfoRepository stubContactInfoRepository;

    private ContactsInteractor interactor;

    @Before
    public void before() {
        stubContactRepository = new ContactRepositoryStubImpl();
        stubContactInfoRepository = new ContactInfoRepositoryStubImpl();
        interactor = new ContactsInteractorImpl(stubContactInfoRepository, stubContactRepository);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void whenCallGetContactsThenReturnAllContacts() {
        List<Contact> contacts = Arrays.asList(
                new Contact(1, "Name1", "123", "uri"),
                new Contact(2, "Name2", "123", "uri")
        );
        ((ContactRepositoryStubImpl) stubContactRepository).addContacts(contacts);
        TestObserver<List<Contact>> testObserver = new TestObserver<>();
        interactor.getContacts().subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertResult(contacts);
        testObserver.assertOf(listTestObserver -> listTestObserver.onSuccess(contacts));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void whenCallGetContactsFromEmptyRepositoryThenReturnEmptyList() {
        List<Contact> contacts = new ArrayList<>();
        TestObserver<List<Contact>> testObserver = new TestObserver<>();
        interactor.getContacts().subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertResult(contacts);
        testObserver.assertOf(listTestObserver -> listTestObserver.onSuccess(contacts));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void whenCallGetContactsByNameThenReturnContactsByThisName() {
        List<Contact> contacts = Arrays.asList(
                new Contact(1, "Name1", "123", "uri"),
                new Contact(2, "Name2", "123", "uri"),
                new Contact(3, "Test1", "123", "uri")
        );
        List<Contact> result = Arrays.asList(contacts.get(0), contacts.get(1));
        ((ContactRepositoryStubImpl) stubContactRepository).addContacts(contacts);
        TestObserver<List<Contact>> testObserver = new TestObserver<>();
        interactor.getContactsByName("Name").subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertResult(result);
        testObserver.assertOf(listTestObserver -> listTestObserver.onSuccess(result));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void whenCallGetContactsByNameWhichNotExistsThenReturnEmptyList() {
        List<Contact> contacts = new ArrayList<>();
        TestObserver<List<Contact>> testObserver = new TestObserver<>();
        interactor.getContactsByName("Name").subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertResult(contacts);
        testObserver.assertOf(listTestObserver -> listTestObserver.onSuccess(contacts));
    }

    @Test
    public void whenCallDeleteEmptyRowsThenEmptyRowsDeletedFromContactInfoRepository() {
        stubContactInfoRepository.insert(new ContactInfo(1, "1.2", "1.3", "address"));
        List<Contact> contacts = new ArrayList<>();
        TestObserver<Completable> testObserver = new TestObserver<>();
        interactor.deleteEmptyRows(contacts).subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertOf(TestObserver::onComplete);
    }
}