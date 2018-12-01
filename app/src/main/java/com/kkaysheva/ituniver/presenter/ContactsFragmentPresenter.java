package com.kkaysheva.ituniver.presenter;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.App;
import com.kkaysheva.ituniver.Screens;
import com.kkaysheva.ituniver.view.ContactsFragmentView;
import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.model.ContactFetcher;

import java.lang.ref.WeakReference;
import java.util.List;

import ru.terrakok.cicerone.Router;

/**
 * ContactsFragmentPresenter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */

@InjectViewState
public final class ContactsFragmentPresenter extends MvpPresenter<ContactsFragmentView> {

    private final Router router;
    private LoadContactsAsyncTask task;

    public ContactsFragmentPresenter() {
        router = App.instance.getRouter();
    }

    public void load() {
        task = new LoadContactsAsyncTask(this);
        task.execute();
    }

    public void onForwardCommandClick(int contactId) {
        router.navigateTo(new Screens.ContactScreen(contactId));
    }

    public void showMessage(int message) {
        getViewState().showMessage(message);
    }

    public void hideMessage() {
        getViewState().hideMessage();
    }

    @Override
    public void onDestroy() {
        if (task != null) {
            task.cancel(true);
        }
        task = null;
        super.onDestroy();
    }

    static final class LoadContactsAsyncTask extends AsyncTask<Void, Void, List<Contact>> {

        private final WeakReference<ContactsFragmentPresenter> reference;

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
