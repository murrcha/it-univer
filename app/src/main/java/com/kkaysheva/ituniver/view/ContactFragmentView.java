package com.kkaysheva.ituniver.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kkaysheva.ituniver.model.Contact;

/**
 * ContactFragmentView
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public interface ContactFragmentView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void loadContact(Contact contact);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showProgress(boolean isLoading);
}
