package com.kkaysheva.ituniver;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

/**
 * ContactFragment
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class ContactFragment extends Fragment {

    public static final String CONTACT_ID = "contactId";

    private int contactId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            contactId = savedInstanceState.getInt(CONTACT_ID);
        }
        if (getArguments() != null) {
            contactId = getArguments().getInt(CONTACT_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
        TextView id = view.findViewById(R.id.id_detail);
        TextView name = view.findViewById(R.id.name_detail);
        TextView number = view.findViewById(R.id.number_detail);
        ImageView photo = view.findViewById(R.id.photo_detail);
        id.setText(String.valueOf(contactId));
        Contact contact = ContactFetcher.getContactById(this.contactId, Objects.requireNonNull(getActivity()));
        if (contact != null) {
            name.setText(contact.getName());
            number.setText(contact.getNumber());
            if (contact.getPhotoUri() != null) {
                Uri uri = Uri.parse(contact.getPhotoUri());
                photo.setImageURI(uri);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CONTACT_ID, contactId);
    }
}
