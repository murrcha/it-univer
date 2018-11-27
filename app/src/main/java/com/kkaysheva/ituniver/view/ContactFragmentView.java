package com.kkaysheva.ituniver.view;

import com.arellomobile.mvp.MvpView;
import com.kkaysheva.ituniver.model.Contact;

/**
 * ContactFragmentView
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public interface ContactFragmentView extends MvpView {

    void loadContact(Contact contact);
    void showProgress(boolean isLoading);
}
