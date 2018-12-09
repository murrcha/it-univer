package com.kkaysheva.ituniver.presenter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.App;
import com.kkaysheva.ituniver.Screens;
import com.kkaysheva.ituniver.view.ContactFragmentView;
import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.model.ContactFetcher;

import ru.terrakok.cicerone.Router;

/**
 * ContactFragmentPresenter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */

@InjectViewState
public final class ContactFragmentPresenter extends MvpPresenter<ContactFragmentView> {

    private final Router router;
    private LoadContactAsyncTask task;

    public ContactFragmentPresenter() {
        this.router = App.instance.getRouter();
    }

    @Override
    public void onDestroy() {
        if (task != null) {
            task.cancel(true);
        }
        task = null;
        super.onDestroy();
    }

    public void fetchContact(int contactId) {
        task = new LoadContactAsyncTask();
        task.execute(contactId);
    }

    public void onForwardCommandClick() {
        router.navigateTo(new Screens.MapScreen());
    }

    @SuppressLint("StaticFieldLeak")
    final class LoadContactAsyncTask extends AsyncTask<Integer, Void, Contact> {

        @Override
        protected void onPreExecute() {
            if (!isCancelled()) {
                getViewState().showProgress(true);
            }
        }

        @Override
        protected Contact doInBackground(Integer... integers) {
            if (!isCancelled()) {
                return ContactFetcher.getContactById(integers[0], App.getContext());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Contact contact) {
            getViewState().loadContact(contact);
            getViewState().showProgress(false);
        }
    }
}
