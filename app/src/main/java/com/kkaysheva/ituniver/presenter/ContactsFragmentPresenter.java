package com.kkaysheva.ituniver.presenter;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.App;
import com.kkaysheva.ituniver.view.ContactsFragmentView;
import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.model.ContactFetcher;

import java.util.List;

/**
 * ContactsFragmentPresenter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */

@InjectViewState
public class ContactsFragmentPresenter extends MvpPresenter<ContactsFragmentView> {

    public void updateUI() {
        new LoadContactsAsyncTask(this)
                .execute();
    }

    static class LoadContactsAsyncTask extends AsyncTask<Void, Void, List<Contact>> {

        private final ContactsFragmentPresenter contactsFragmentPresenter;

        public LoadContactsAsyncTask(ContactsFragmentPresenter contactsFragmentPresenter) {
            this.contactsFragmentPresenter = contactsFragmentPresenter;
        }

        @Override
        protected void onPreExecute() {
            contactsFragmentPresenter.getViewState().showProgress(true);
        }

        @Override
        protected List<Contact> doInBackground(Void... voids) {
            return ContactFetcher.getContacts(App.getContext());
        }

        @Override
        protected void onPostExecute(List<Contact> contacts) {
            contactsFragmentPresenter.getViewState().loadContacts(contacts);
            contactsFragmentPresenter.getViewState().showProgress(false);
        }
    }
}
