package com.kkaysheva.ituniver.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kkaysheva.ituniver.model.Contact;

/**
 * ContactFragmentView
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public interface ContactFragmentView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void loadContact(Contact contact);

    @StateStrategyType(SingleStateStrategy.class)
    void showProgress(boolean isLoading);
}
