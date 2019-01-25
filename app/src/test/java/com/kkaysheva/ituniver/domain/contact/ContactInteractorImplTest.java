package com.kkaysheva.ituniver.domain.contact;

import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.domain.ContactRepository;
import com.kkaysheva.ituniver.domain.model.Contact;
import com.kkaysheva.ituniver.domain.model.ContactInfo;
import com.kkaysheva.ituniver.domain.stubs.ContactInfoRepositoryStubImpl;
import com.kkaysheva.ituniver.domain.stubs.ContactRepositoryStubImpl;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Completable;
import io.reactivex.observers.TestObserver;

/**
 * ContactInteractorImplTest
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class ContactInteractorImplTest {

    private ContactRepository stubContactRepository;

    private ContactInfoRepository stubContactInfoRepository;

    private ContactInteractor interactor;

    @Before
    public void before() {
        stubContactRepository = new ContactRepositoryStubImpl();
        stubContactInfoRepository = new ContactInfoRepositoryStubImpl();
        interactor = new ContactInteractorImpl(stubContactInfoRepository, stubContactRepository);
    }

    @Test
    public void whenGetContactByIdThenReturnContact() {
        Contact contact = new Contact(1, "Test", "123", "test");
        ((ContactRepositoryStubImpl) stubContactRepository).addContact(contact);
        TestObserver<Contact> testObserver = new TestObserver<>();
        interactor.getContactById(1).subscribe(testObserver);
        testObserver
                .assertSubscribed()
                .assertResult(contact);
    }

    @Test
    public void whenGetContactByInvalidIdThenReturnError() {
        TestObserver<Contact> testObserver = new TestObserver<>();
        interactor.getContactById(1).subscribe(testObserver);
        testObserver
                .assertSubscribed()
                .assertNoValues()
                .assertError(NullPointerException.class)
                .onComplete();
    }

    @Test
    public void whenGetContactInfoByIdThenReturnContactInfo() {
        ContactInfo contactInfo = new ContactInfo(1, "1.2", "1.3", "address");
        TestObserver<Completable> completableTestObserver = new TestObserver<>();
        stubContactInfoRepository.insert(contactInfo).subscribe(completableTestObserver);
        completableTestObserver.assertSubscribed();
        TestObserver<ContactInfo> testObserver = new TestObserver<>();
        interactor.getContactInfoById(1).subscribe(testObserver);
        testObserver
                .assertSubscribed()
                .assertResult(contactInfo);
    }

    @Test
    public void whenGetContactInfoByInvalidIdThenReturnError() {
        TestObserver<ContactInfo> testObserver = new TestObserver<>();
        interactor.getContactInfoById(1).subscribe(testObserver);
        testObserver
                .assertSubscribed()
                .assertNoValues()
                .assertNoErrors()
                .assertComplete();
    }
}