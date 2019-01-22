package com.kkaysheva.ituniver.presentation.main;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kkaysheva.ituniver.R;
import com.kkaysheva.ituniver.presentation.app.AppDelegate;
import com.kkaysheva.ituniver.di.main.MainComponent;
import com.kkaysheva.ituniver.di.main.MainModule;

import javax.inject.Inject;
import javax.inject.Provider;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

/**
 * MainActivity
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public final class MainActivity extends MvpAppCompatActivity implements MainView {

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    Navigator navigator;

    @Inject
    Provider<MainPresenter> presenterProvider;

    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AppDelegate appDelegate = (AppDelegate) getApplication();
        MainComponent mainComponent = appDelegate.getAppComponent()
                .plusMainComponent(new MainModule(this, getSupportFragmentManager()));
        mainComponent.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
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

    @ProvidePresenter
    MainPresenter providePresenter() {
        return presenterProvider.get();
    }
}
