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

    public void fetchContacts() {
        task = new LoadContactsAsyncTask();
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

    public void search(String query) {
        getViewState().search(query);
    }

    @Override
    public void onDestroy() {
        if (task != null) {
            task.cancel(true);
        }
        task = null;
        super.onDestroy();
    }

    @SuppressLint("StaticFieldLeak")
    final class LoadContactsAsyncTask extends AsyncTask<Void, Void, List<Contact>> {

        @Override
        protected void onPreExecute() {
            if (!isCancelled()) {
                getViewState().showProgress(true);
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
            getViewState().loadContacts(contacts);
            getViewState().showProgress(false);
        }
    }
}
