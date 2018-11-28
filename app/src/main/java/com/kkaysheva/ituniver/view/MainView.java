package com.kkaysheva.ituniver.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface MainView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void addContactsFragment();

    @StateStrategyType(SingleStateStrategy.class)
    void replaceContactFragment();
}
