package com.kkaysheva.ituniver.presentation.contact;

import com.kkaysheva.ituniver.domain.contact.ContactInteractor;
import com.kkaysheva.ituniver.presentation.Screens;
import com.kkaysheva.ituniver.rules.TestSchedulerRule;
import com.kkaysheva.ituniver.stubs.interactor.ContactInteractorStub;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.terrakok.cicerone.Router;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * ContactPresenterTest
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public class ContactPresenterTest {

    @Rule
    public final TestSchedulerRule testSchedulerRule = new TestSchedulerRule();

    private ContactInteractor stubInteractor;

    private ContactPresenter presenter;

    @Mock
    private Router router;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        stubInteractor = new ContactInteractorStub();
        presenter = new ContactPresenter(router, stubInteractor);
    }

    @Test
    public void whenCallFetchContactThenReturnContact() {
        presenter.fetchContact(1);
        stubInteractor.getContactById(1).test()
                .assertSubscribed()
                .assertValue(contact -> contact.getId() == 1)
                .assertNoErrors()
                .assertComplete()
                .dispose();
    }

    @Test
    public void whenCallFetchContactThenReturnError() {
        presenter.fetchContact(0);
        stubInteractor.getContactById(0).test()
                .assertSubscribed()
                .assertError(NullPointerException.class)
                .dispose();
    }

    @Test
    public void whenCallFetchContactInfoThenReturnContactInfo() {
        presenter.fetchContactInfo(1);
        stubInteractor.getContactInfoById(1).test()
                .assertSubscribed()
                .assertValue(contactInfo -> contactInfo.getId() == 1)
                .assertNoErrors()
                .assertComplete()
                .dispose();
    }

    @Test
    public void whenCallFetchContactInfoThenReturnError() {
        presenter.fetchContactInfo(0);
        stubInteractor.getContactInfoById(0).test()
                .assertSubscribed()
                .assertNoValues()
                .assertNoErrors()
                .assertComplete()
                .dispose();
    }

    @Test
    public void whenCallOnForwardCommandClickThenNavigateToMapScreen() {
        presenter.onForwardCommandClick(1);
        verify(router).navigateTo(any(Screens.ContactMapScreen.class));
    }
}