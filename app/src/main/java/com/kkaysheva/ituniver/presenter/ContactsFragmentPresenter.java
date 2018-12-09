package com.kkaysheva.ituniver.presenter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.App;
import com.kkaysheva.ituniver.Screens;
import com.kkaysheva.ituniver.view.ContactsFragmentView;
import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.model.ContactFetcher;

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

    @Override
    public void onDestroy() {
        if (task != null) {
            task.cancel(true);
        }
        task = null;
        super.onDestroy();
    }

    public void fetchContacts() {
        task = new LoadContactsAsyncTask();
        task.execute();
    }

    public void fetchContactsByName(String name) {
        getViewState().saveQuery(name);
        task = new LoadContactsAsyncTask();
        task.execute(name);
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

    @SuppressLint("StaticFieldLeak")
    final class LoadContactsAsyncTask extends AsyncTask<String, Void, List<Contact>> {

        @Override
        protected void onPreExecute() {
            if (!isCancelled()) {
                getViewState().showProgress(true);
            }
        }

        @Override
        protected List<Contact> doInBackground(String... strings) {
            if (isCancelled()) {
                return null;
            }
            if (strings == null || strings.length == 0) {
                return ContactFetcher.getContacts(App.getContext());
            } else {
                return ContactFetcher.getContactsByName(strings[0], App.getContext());
            }
        }

        @Override
        protected void onPostExecute(List<Contact> contacts) {
            getViewState().loadContacts(contacts);
            getViewState().showProgress(false);
        }
    }
}
