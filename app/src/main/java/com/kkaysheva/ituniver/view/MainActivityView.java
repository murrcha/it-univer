package com.kkaysheva.ituniver.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kkaysheva.ituniver.Screens;

/**
 * MainActivityView
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public interface MainActivityView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void addContacts(Screens.ContactsScreen screen);
}
