package com.kkaysheva.ituniver.view;

import com.arellomobile.mvp.MvpView;
import com.kkaysheva.ituniver.model.Contact;

import java.util.List;

/**
 * ContactsFragmentView
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public interface ContactsFragmentView extends MvpView {

    void loadContacts(List<Contact> contacts);
    void showProgress(boolean isLoading);
}
