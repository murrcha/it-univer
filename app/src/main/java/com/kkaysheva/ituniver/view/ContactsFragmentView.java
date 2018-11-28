package com.kkaysheva.ituniver.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kkaysheva.ituniver.model.Contact;

import java.util.List;

/**
 * ContactsFragmentView
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public interface ContactsFragmentView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void loadContacts(List<Contact> contacts);

    @StateStrategyType(SingleStateStrategy.class)
    void showProgress(boolean isLoading);
}
