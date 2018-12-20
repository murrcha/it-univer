package com.kkaysheva.ituniver.presentation.contact;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kkaysheva.ituniver.R;
import com.kkaysheva.ituniver.app.AppDelegate;
import com.kkaysheva.ituniver.di.contact.ContactComponent;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * ContactFragment
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public final class ContactFragment extends MvpAppCompatFragment implements ContactView {

    private static final String TAG = ContactFragment.class.getSimpleName();
    private static final int PERMISSION_REQUEST_READ_CONTACTS = 1;
    private static final String CONTACT_ID = "contactId";

    @Inject
    Provider<ContactPresenter> presenterProvider;

    @InjectPresenter
    ContactPresenter presenter;

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
    public void onAttach(Context context) {
        AppDelegate appDelegate = (AppDelegate) requireActivity().getApplication();
        ContactComponent contactComponent = appDelegate.getAppComponent()
                .plusContactComponent();
        contactComponent.inject(this);
        super.onAttach(context);
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
        setHasOptionsMenu(true);
        TextView id = view.findViewById(R.id.id_detail);
        name = view.findViewById(R.id.name_detail);
        number = view.findViewById(R.id.number_detail);
        photo = view.findViewById(R.id.photo_detail);
        progressBar = view.findViewById(R.id.progress_contact_load);
        Toolbar toolbar = view.findViewById(R.id.contact_toolbar);
        toolbar.setTitle(R.string.contact_title);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id.setText(String.valueOf(contactId));
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onViewCreated: permission granted, fetchContacts contact");
            presenter.fetchContact(contactId);
        } else {
            Log.d(TAG, "onViewCreated: permission denied, request permissions");
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onDestroyView() {
        name = null;
        number = null;
        photo = null;
        progressBar = null;
        super.onDestroyView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.fetchContact(contactId);
            } else {
                name.setText(R.string.no_permissions);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_contact, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.location_contact:
                presenter.onForwardCommandClick();
                Toast.makeText(requireContext(), "Go to map", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void loadContact(com.kkaysheva.ituniver.model.Contact contact) {
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

    @ProvidePresenter
    ContactPresenter providePresenter() {
        return presenterProvider.get();
    }
}
