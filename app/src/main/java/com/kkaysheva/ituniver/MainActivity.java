package com.kkaysheva.ituniver;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kkaysheva.ituniver.presenter.MainActivityPresenter;
import com.kkaysheva.ituniver.view.MainActivityView;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

/**
 * MainActivity
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public final class MainActivity extends MvpAppCompatActivity implements MainActivityView {

    @InjectPresenter
    MainActivityPresenter presenter;

    private Navigator navigator = new SupportAppNavigator(
            MainActivity.this,
            getSupportFragmentManager(),
            R.id.fragment_container);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        App.instance.getNavigatorHolder().setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        App.instance.getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
