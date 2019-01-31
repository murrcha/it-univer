package com.kkaysheva.ituniver.presentation.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kkaysheva.ituniver.R;
import com.kkaysheva.ituniver.presentation.app.UniverApplication;

import javax.inject.Inject;
import javax.inject.Provider;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

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
    Provider<MainPresenter> presenterProvider;

    @InjectPresenter
    MainPresenter presenter;

    private Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectMainComponent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigator = getNavigator(this, getSupportFragmentManager(), getContainer());
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
    protected void onDestroy() {
        navigator = null;
        super.onDestroy();
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

    private void injectMainComponent() {
        ((UniverApplication) getApplication())
                .getApplicationComponent()
                .plusMainComponent()
                .inject(this);
    }

    private int getContainer() {
        return R.id.fragment_container;
    }

    private Navigator getNavigator(FragmentActivity activity, FragmentManager fragmentManager, int container) {
        return new SupportAppNavigator(activity, fragmentManager, container);
    }
}
