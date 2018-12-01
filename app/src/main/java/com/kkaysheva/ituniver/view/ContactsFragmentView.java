package com.kkaysheva.ituniver.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
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

    @StateStrategyType(OneExecutionStateStrategy.class)
    void loadContacts(List<Contact> contacts);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showProgress(boolean isLoading);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMessage(int message);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideMessage();
}
