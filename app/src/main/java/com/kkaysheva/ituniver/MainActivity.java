package com.kkaysheva.ituniver;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * MainActivity
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class MainActivity extends AppCompatActivity implements ContactsFragment.CLickContactCallback {

    private FragmentManager fragmentManager;
    private Fragment contactFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        Fragment contactsFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (contactsFragment == null) {
            contactsFragment = new ContactsFragment();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, contactsFragment)
                .commit();
    }

    @Override
    public void contactClicked(int contactId) {
        if (contactFragment == null) {
            contactFragment = new ContactFragment();
        }
        Bundle args = new Bundle();
        args.putInt(ContactFragment.CONTACT_ID, contactId);
        contactFragment.setArguments(args);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, contactFragment)
                .addToBackStack(null)
                .commit();
    }
}
