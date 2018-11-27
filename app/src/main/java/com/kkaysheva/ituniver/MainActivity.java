package com.kkaysheva.ituniver;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kkaysheva.ituniver.presenter.MainActivityPresenter;
import com.kkaysheva.ituniver.view.MainView;

/**
 * MainActivity
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class MainActivity extends MvpAppCompatActivity implements ContactsFragment.ClickContactCallback, MainView {

    @InjectPresenter
    MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, ContactsFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void contactClicked(int contactId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ContactFragment.newInstance(contactId))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addContactsFragment() {
        //todo
    }

    @Override
    public void replaceContactFragment() {
        //todo
    }
}
