package com.kkaysheva.ituniver;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kkaysheva.ituniver.model.Contact;
import com.kkaysheva.ituniver.presenter.ContactFragmentPresenter;
import com.kkaysheva.ituniver.view.ContactFragmentView;

/**
 * ContactFragment
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class ContactFragment extends MvpAppCompatFragment implements ContactFragmentView {

    private static final String TAG = ContactFragment.class.getSimpleName();
    private static final int PERMISSION_REQUEST_READ_CONTACTS = 1;
    private static final String CONTACT_ID = "contactId";

    @InjectPresenter
    ContactFragmentPresenter contactFragmentPresenter;

    private int contactId;
    private TextView name;
    private TextView number;
    private ImageView photo;
    private ProgressBar progressBar;

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
        progressBar = view.findViewById(R.id.progress_contact_load);
        id.setText(String.valueOf(contactId));
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onViewCreated: permission granted, load contact");
            contactFragmentPresenter.updateUI(contactId);
        } else {
            Log.d(TAG, "onViewCreated: permission denied, request permissions");
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                contactFragmentPresenter.updateUI(contactId);
            } else {
                name.setText(R.string.no_permissions);
            }
        }
    }

    @Override
    public void loadContact(Contact contact) {
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

    @Override
    public void showProgress(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
