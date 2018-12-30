package com.kkaysheva.ituniver;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kkaysheva.ituniver.adapter.ContactAdapter;
import com.kkaysheva.ituniver.adapter.ContactItemDecoration;
import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.presenter.ContactsFragmentPresenter;
import com.kkaysheva.ituniver.view.ContactsFragmentView;

import java.util.List;

/**
 * ContactsFragment
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public final class ContactsFragment extends MvpAppCompatFragment implements ContactsFragmentView {

    private static final String TAG = ContactsFragment.class.getSimpleName();
    private static final int PERMISSION_REQUEST_READ_CONTACTS = 1;

    @InjectPresenter
    ContactsFragmentPresenter presenter;

    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private TextView message;
    private ProgressBar progressBar;
    private String searchQuery;
    private boolean isGranted = false;

    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initRecyclerView(view);
        progressBar = view.findViewById(R.id.progress_contacts_load);
        Toolbar toolbar = view.findViewById(R.id.contacts_toolbar);
        toolbar.setTitle(R.string.contacts_title);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        if (ContextCompat.checkSelfPermission(App.getContext(), Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "fetchContacts: permission granted, update ui");
            presenter.fetchContacts();
            reloadOptionsMenu();
        } else {
            Log.d(TAG, "fetchContacts: permission denied, request permission");
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_READ_CONTACTS);
        }
        presenter.hideMessage();
    }

    @Override
    public void onDestroyView() {
        adapter = null;
        message = null;
        progressBar = null;
        recyclerView.setAdapter(null);
        super.onDestroyView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.hideMessage();
        if (requestCode == PERMISSION_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: permissions accept, fetchContacts contacts");
                presenter.fetchContacts();
                reloadOptionsMenu();
            } else {
                Log.d(TAG, "onRequestPermissionsResult: permission denied, set holder text");
                presenter.showMessage(R.string.no_permissions);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_contacts, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem syncItem = menu.findItem(R.id.action_sync);
        searchItem.setVisible(isGranted);
        syncItem.setVisible(isGranted);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        if (searchQuery != null && !searchQuery.isEmpty()) {
            searchItem.expandActionView();
            searchView.setQuery(searchQuery, true);
            searchView.clearFocus();
            presenter.fetchContactsByName(searchQuery);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.fetchContactsByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.fetchContactsByName(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sync:
                presenter.fetchContacts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initRecyclerView(@NonNull final View view) {
        recyclerView = view.findViewById(R.id.contacts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(
                new ContactItemDecoration(getResources().getDimensionPixelSize(R.dimen.margin_card_view)));
        adapter = new ContactAdapter();
        adapter.setOnClickListener(presenter::onForwardCommandClick);
        recyclerView.setAdapter(adapter);
        message = view.findViewById(R.id.contacts_message);
    }

    private void reloadOptionsMenu() {
        isGranted = true;
        requireActivity().invalidateOptionsMenu();
    }

    @Override
    public void loadContacts(List<Contact> contacts) {
        adapter.submitItems(contacts);
        if (contacts.isEmpty()) {
            presenter.showMessage(R.string.no_contacts);
        } else {
            presenter.hideMessage();
        }
    }

    @Override
    public void showProgress(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMessage(int message) {
        this.message.setVisibility(View.VISIBLE);
        this.message.setText(message);
    }

    @Override
    public void hideMessage() {
        this.message.setVisibility(View.GONE);
    }

    @Override
    public void saveQuery(String query) {
        searchQuery = query;
    }
}
