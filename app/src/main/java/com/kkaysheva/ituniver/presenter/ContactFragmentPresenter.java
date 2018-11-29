package com.kkaysheva.ituniver.presenter;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kkaysheva.ituniver.App;
import com.kkaysheva.ituniver.view.ContactFragmentView;
import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.model.ContactFetcher;

import java.lang.ref.WeakReference;

/**
 * ContactFragmentPresenter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */

@InjectViewState
public final class ContactFragmentPresenter extends MvpPresenter<ContactFragmentView> {

    private LoadContactAsyncTask task;

    public void load(int contactId) {
        task = new LoadContactAsyncTask(this);
        task.execute(contactId);
    }

    @Override
    public void onDestroy() {
        if (task != null) {
            task.cancel(true);
        }
        task = null;
        super.onDestroy();
    }

    static final class LoadContactAsyncTask extends AsyncTask<Integer, Void, Contact> {

        private final WeakReference<ContactFragmentPresenter> reference;

        public LoadContactAsyncTask(ContactFragmentPresenter presenter) {
            reference = new WeakReference<>(presenter);
        }

        @Override
        protected void onPreExecute() {
            ContactFragmentPresenter presenter = reference.get();
            if (!isCancelled() && presenter != null) {
                presenter.getViewState().showProgress(true);
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
            ContactFragmentPresenter presenter = reference.get();
            if (presenter != null) {
                presenter.getViewState().loadContact(contact);
                presenter.getViewState().showProgress(false);
            }
        }
    }
}
