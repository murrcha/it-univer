package com.kkaysheva.ituniver.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.Screens;
import com.kkaysheva.ituniver.view.MainActivityView;

/**
 * MainActivityPresenter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityView> {

    public static final String TAG = MainActivityPresenter.class.getSimpleName();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.d(TAG, "onFirstViewAttach: addContacts");
        addContacts();
    }

    private void addContacts() {
        Log.d(TAG, "addContacts: restore = " + isInRestoreState(getViewState()));
        getViewState().addContacts(new Screens.ContactsScreen());
    }
}
