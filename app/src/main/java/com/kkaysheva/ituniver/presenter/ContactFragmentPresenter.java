package com.kkaysheva.ituniver.presenter;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.App;
import com.kkaysheva.ituniver.view.ContactFragmentView;
import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.model.ContactFetcher;

/**
 * ContactFragmentPresenter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */

@InjectViewState
public class ContactFragmentPresenter extends MvpPresenter<ContactFragmentView> {

    public void updateUI(int contactId) {
        new LoadContactAsyncTask(this)
                .execute(contactId);
    }

    static class LoadContactAsyncTask extends AsyncTask<Integer, Void, Contact> {

        private final ContactFragmentPresenter contactFragmentPresenter;

        public LoadContactAsyncTask(ContactFragmentPresenter contactFragmentPresenter) {
            this.contactFragmentPresenter = contactFragmentPresenter;
        }

        @Override
        protected void onPreExecute() {
            contactFragmentPresenter.getViewState().showProgress(true);
        }

        @Override
        protected Contact doInBackground(Integer... integers) {
            return ContactFetcher.getContactById(integers[0], App.getContext());
        }

        @Override
        protected void onPostExecute(Contact contact) {
            contactFragmentPresenter.getViewState().loadContact(contact);
            contactFragmentPresenter.getViewState().showProgress(false);
        }
    }
}
