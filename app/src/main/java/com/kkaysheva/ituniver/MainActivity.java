package com.kkaysheva.ituniver;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * MainActivity
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class MainActivity extends AppCompatActivity implements ContactsFragment.CLickContactCallback {

    private FragmentManager fragmentManager;
    private Fragment contactFragment;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                actionBar.setDisplayHomeAsUpEnabled(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        actionBar.setDisplayHomeAsUpEnabled(false);
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
