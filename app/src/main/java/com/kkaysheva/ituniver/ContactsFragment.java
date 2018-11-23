package com.kkaysheva.ituniver;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

/**
 * ContactsFragment
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class ContactsFragment extends Fragment {

    private CLickContactCallback callback;

    public interface CLickContactCallback {
        void contactClicked(int contactId);
    }

    private void initRecyclerView(@NonNull final View view) {
        RecyclerView recyclerView = view.findViewById(R.id.contacts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        ContactAdapter adapter = new ContactAdapter();
        adapter.setOnClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int contactId) {
                callback.contactClicked(contactId);
            }
        });
        TextView noContacts = view.findViewById(R.id.no_contacts);
        List<Contact> contacts = ContactFetcher.getContacts(Objects.requireNonNull(getActivity()));
        if (contacts != null && !contacts.isEmpty()) {
            noContacts.setVisibility(View.GONE);
            adapter.setItems(contacts);
            recyclerView.setAdapter(adapter);
        } else {
            noContacts.setVisibility(View.VISIBLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (CLickContactCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
