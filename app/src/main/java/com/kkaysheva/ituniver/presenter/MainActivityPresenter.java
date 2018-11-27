package com.kkaysheva.ituniver.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.view.MainView;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainView> {

    public MainActivityPresenter() {
        getViewState().addContactsFragment();
        getViewState().replaceContactFragment();
    }
}
