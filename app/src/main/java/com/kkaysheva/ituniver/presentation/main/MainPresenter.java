package com.kkaysheva.ituniver.presentation.main;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.Screens;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

/**
 * MainPresenter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @NonNull
    private final Router router;

    @Inject
    public MainPresenter(Router router) {
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (!isInRestoreState(getViewState())) {
            addContactsScreen();
        }
    }

    private void addContactsScreen() {
        router.newRootScreen(new Screens.ContactsScreen());
    }
}
