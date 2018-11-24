package com.kkaysheva.ituniver;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ContactFragment
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class ContactFragment extends Fragment {

    private static final String TAG = ContactFragment.class.getSimpleName();
    private static final int PERMISSION_REQUEST_READ_CONTACTS = 1;
    private static final String CONTACT_ID = "contactId";

    private int contactId;
    private TextView name;
    private TextView number;
    private ImageView photo;
    private LoaderThread loaderThread;

    public static ContactFragment newInstance(int contactId) {
        Bundle args = new Bundle();
        args.putInt(CONTACT_ID, contactId);
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contactId = getArguments().getInt(CONTACT_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) requireActivity())
                .getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        TextView id = view.findViewById(R.id.id_detail);
        name = view.findViewById(R.id.name_detail);
        number = view.findViewById(R.id.number_detail);
        photo = view.findViewById(R.id.photo_detail);
        id.setText(String.valueOf(contactId));
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onViewCreated: permission granted, load contact");
            loaderThread = new LoaderThread(requireActivity());
            loaderThread.start();
        } else {
            Log.d(TAG, "onViewCreated: permission denied, request permissions");
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loaderThread = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loaderThread = new LoaderThread(requireActivity());
                loaderThread.start();
            } else {
                name.setText(R.string.no_permissions);
            }
        }
    }

    private void loadContact(Contact contact) {
        if (contact != null) {
            name.setText(contact.getName());
            number.setText(contact.getNumber());
            if (contact.getPhotoUri() != null) {
                Uri uri = Uri.parse(contact.getPhotoUri());
                photo.setImageURI(uri);
            }
        } else {
            Log.d(TAG, "loadContact: contact is null");
        }
    }

    private class LoaderThread extends Thread {

        private final Context context;

        public LoaderThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            Contact contact = ContactFetcher.getContactById(contactId, context);
            new Handler(Looper.getMainLooper()).post(() -> loadContact(contact));
        }
    }
}
