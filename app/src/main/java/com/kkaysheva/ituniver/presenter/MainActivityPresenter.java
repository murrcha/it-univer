package com.kkaysheva.ituniver.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.App;
import com.kkaysheva.ituniver.Screens;
import com.kkaysheva.ituniver.view.MainActivityView;

import ru.terrakok.cicerone.Router;

/**
 * MainActivityPresenter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityView> {

    private final Router router;

    public MainActivityPresenter() {
        router = App.instance.getRouter();
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
