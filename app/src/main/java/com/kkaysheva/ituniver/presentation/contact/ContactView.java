package com.kkaysheva.ituniver.presentation.contact;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.model.ContactInfo;

/**
 * ContactView
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public interface ContactView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showContact(Contact contact);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showContactInfo(ContactInfo contactInfo);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showProgress(boolean isLoading);
}
