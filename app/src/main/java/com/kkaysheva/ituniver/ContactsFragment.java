package com.kkaysheva.ituniver;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * ContactsFragment
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class ContactsFragment extends Fragment {

    private static final String TAG = ContactsFragment.class.getSimpleName();
    private static final int PERMISSION_REQUEST_READ_CONTACTS = 1;

    private ClickContactCallback callback;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private TextView noContacts;
    private LoaderThread loaderThread;

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
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "initRecyclerView: permission accept, load contact");
            loaderThread = new LoaderThread(requireActivity());
            loaderThread.start();
        } else {
            Log.d(TAG, "initRecyclerView: permission denied, request permissions");
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ClickContactCallback) context;
    }

    @Override
    public void onDestroyView() {
        loaderThread = null;
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: permissions accept, load contacts");
                loaderThread = new LoaderThread(requireActivity());
                loaderThread.start();
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
        adapter.setOnClickListener(callback::contactClicked);
        noContacts = view.findViewById(R.id.no_contacts);
    }

    private void loadContacts(List<Contact> contacts) {
        if (!contacts.isEmpty()) {
            noContacts.setVisibility(View.GONE);
            adapter.setItems(contacts);
            recyclerView.setAdapter(adapter);
        } else {
            noContacts.setVisibility(View.VISIBLE);
            noContacts.setText(R.string.no_contacts);
        }
    }

    public interface ClickContactCallback {
        void contactClicked(int contactId);
    }

    private static class LoaderThread extends Thread {

        private final WeakReference<FragmentActivity> activityWeakReference;
        private final Handler handler;

        LoaderThread(FragmentActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
            handler = new Handler(Looper.getMainLooper());
        }

        @Override
        public void run() {
            FragmentActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            List<Contact> contacts = ContactFetcher.getContacts(activity);
            Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragment instanceof ContactsFragment) {
                handler.post(() -> ((ContactsFragment) fragment).loadContacts(contacts));
            }
        }
    }
}
