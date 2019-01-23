package com.kkaysheva.ituniver.domain.contact;

import android.content.Context;

import com.kkaysheva.ituniver.domain.ContactInfoRepository;
import com.kkaysheva.ituniver.domain.ContactRepository;
import com.kkaysheva.ituniver.domain.tools.ContactInfoRepositoryStubImpl;
import com.kkaysheva.ituniver.domain.tools.ContactRepositoryStubImpl;
import com.kkaysheva.ituniver.domain.model.Contact;
import com.kkaysheva.ituniver.domain.model.ContactInfo;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * ContactInteractorImplTest
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public class ContactInteractorImplTest {

    private ContactRepository contactRepository;

    private ContactInfoRepository contactInfoRepository;

    private ContactInteractor interactor;

    @Inject
    private Context context;

    @Before
    public void beforeTest() {
        contactRepository = new ContactRepositoryStubImpl();
        contactInfoRepository = new ContactInfoRepositoryStubImpl();
        interactor = new ContactInteractorImpl(context, contactInfoRepository, contactRepository);
    }

    @Test
    public void whenGetContactByIdThenReturnContact() {
        Contact contact = new Contact(1, "Name", "123", "uri");
        ((ContactRepositoryStubImpl) contactRepository).addContact(contact);
        assertThat(interactor.getContactById(1), instanceOf(Single.class));
        assertThat(interactor.getContactById(1).blockingGet().getId(), is(1));
        assertThat(interactor.getContactById(1).blockingGet().getName(), is("Name"));
        assertThat(interactor.getContactById(1).blockingGet().getNumber(), is("123"));
        assertThat(interactor.getContactById(1).blockingGet().getPhotoUri(), is("uri"));
    }

    @Test(expected = NullPointerException.class)
    public void whenGetContactByInvalidIdThenReturnError() {
        assertThat(interactor.getContactById(1), nullValue());
    }

    @Test
    public void whenGetContactInfoByIdThenReturnContactInfo() {
        ContactInfo contactInfo = new ContactInfo(1, "1.2", "1.3", "address");
        contactInfoRepository.insert(contactInfo);
        assertThat(interactor.getContactInfoById(1), instanceOf(Maybe.class));
        assertThat(interactor.getContactInfoById(1).blockingGet().getId(), is(1));
        assertThat(interactor.getContactInfoById(1).blockingGet().getLongitude(), is("1.2"));
        assertThat(interactor.getContactInfoById(1).blockingGet().getLatitude(), is("1.3"));
        assertThat(interactor.getContactInfoById(1).blockingGet().getAddress(), is("address"));
    }

    @Test(expected = NullPointerException.class)
    public void whenGetContactInfoByInvalidIdThenReturnError() {
        assertThat(interactor.getContactInfoById(1), nullValue());
    }
}