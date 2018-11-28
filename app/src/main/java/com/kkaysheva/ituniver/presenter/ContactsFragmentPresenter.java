package com.kkaysheva.ituniver.presenter;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.App;
import com.kkaysheva.ituniver.view.ContactsFragmentView;
import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.model.ContactFetcher;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * ContactsFragmentPresenter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */

@InjectViewState
public class ContactsFragmentPresenter extends MvpPresenter<ContactsFragmentView> {

    private LoadContactsAsyncTask task;

    public void load() {
        task = new LoadContactsAsyncTask(this);
        task.execute();
    }

    @Override
    public void onDestroy() {
        task.cancel(true);
        task = null;
        super.onDestroy();
    }

    static class LoadContactsAsyncTask extends AsyncTask<Void, Void, List<Contact>> {

        private WeakReference<ContactsFragmentPresenter> reference;

        public LoadContactsAsyncTask(ContactsFragmentPresenter presenter) {
            reference = new WeakReference<>(presenter);
        }

        @Override
        protected void onPreExecute() {
            ContactsFragmentPresenter presenter = reference.get();
            if (!isCancelled() && presenter != null) {
                presenter.getViewState().showProgress(true);
            }
        }

        @Override
        protected List<Contact> doInBackground(Void... voids) {
            if (!isCancelled()) {
                return ContactFetcher.getContacts(App.getContext());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Contact> contacts) {
            ContactsFragmentPresenter presenter = reference.get();
            if (presenter != null) {
                presenter.getViewState().loadContacts(contacts);
                presenter.getViewState().showProgress(false);
            }
        }
    }
}
