package com.kkaysheva.ituniver;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
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
    private TextView noContacts;
    private ProgressBar progressBar;

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
        initRecyclerView(view);
        progressBar = view.findViewById(R.id.progress_contacts_load);
        if (ContextCompat.checkSelfPermission(App.getContext(), Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "load: permission granted, update ui");
            presenter.load();
        } else {
            Log.d(TAG, "load: permission denied, request permission");
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: permissions accept, load contacts");
                presenter.load();
            } else {
                Log.d(TAG, "onRequestPermissionsResult: permission denied, set holder text");
                noContacts.setVisibility(View.VISIBLE);
                noContacts.setText(R.string.no_permissions);
            }
        }
    }

    private void initRecyclerView(@NonNull final View view) {
        recyclerView = view.findViewById(R.id.contacts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new ContactAdapter();
        adapter.setOnClickListener(presenter::onForwardCommandClick);
        noContacts = view.findViewById(R.id.no_contacts);
    }

    @Override
    public void loadContacts(List<Contact> contacts) {
        if (!contacts.isEmpty()) {
            noContacts.setVisibility(View.GONE);
            adapter.setItems(contacts);
            recyclerView.setAdapter(adapter);
        } else {
            noContacts.setVisibility(View.VISIBLE);
            noContacts.setText(R.string.no_contacts);
        }
    }

    @Override
    public void showProgress(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
