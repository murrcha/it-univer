package com.kkaysheva.ituniver.presentation.contacts;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kkaysheva.ituniver.domain.model.Contact;

import java.util.List;

/**
 * ContactsView
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public interface ContactsView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showContacts(List<Contact> contacts);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showProgress(boolean isLoading);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMessage(int message);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideMessage();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void saveQuery(String query);
}
