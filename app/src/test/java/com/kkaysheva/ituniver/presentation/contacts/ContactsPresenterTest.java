package com.kkaysheva.ituniver.presentation.contacts;

import com.kkaysheva.ituniver.domain.contacts.ContactsInteractor;
import com.kkaysheva.ituniver.presentation.Screens;
import com.kkaysheva.ituniver.rules.TestSchedulerRule;
import com.kkaysheva.ituniver.stubs.interactor.ContactsInteractorStub;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.terrakok.cicerone.Router;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * ContactsPresenterTest
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public class ContactsPresenterTest {

    @Rule
    public final TestSchedulerRule testSchedulerRule = new TestSchedulerRule();

    private ContactsInteractor stubInteractor;

    private ContactsPresenter presenter;

    @Mock
    private Router router;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        stubInteractor = new ContactsInteractorStub();
        presenter = new ContactsPresenter(router, stubInteractor);
    }

    @Test
    public void whenCallFetchContactsThenReturnContacts() {
        presenter.fetchContacts();
        stubInteractor.getContacts().test()
                .assertSubscribed()
                .assertValue(contacts -> contacts.size() == 2)
                .assertNoErrors()
                .assertComplete()
                .dispose();
    }

    @Test
    public void whenCallFetchContactsByNameThenReturnContactsByName() {
        presenter.fetchContactsByName("test");
        stubInteractor.getContactsByName("test").test()
                .assertSubscribed()
                .assertValue(contacts -> contacts.get(0).getName().equals("test"))
                .assertNoErrors()
                .assertComplete()
                .dispose();
    }

    @Test
    public void whenCallOnForwardCommandClickToContactThenNavigateToContactScreen() {
        presenter.onForwardCommandClickToContact(1);
        verify(router).navigateTo(any(Screens.ContactScreen.class));
    }

    @Test
    public void whenCallOnForwardCommandClickToMapThenNavigateToMapScreen() {
        presenter.onForwardCommandClickToMap();
        verify(router).navigateTo(any(Screens.ContactsMapScreen.class));
    }
}