package com.kkaysheva.ituniver.di.main;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.kkaysheva.ituniver.R;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

/**
 * MainModule
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@Module
public final class MainModule {

    @NonNull
    private FragmentActivity activity;

    @NonNull
    private FragmentManager fragmentManager;

    public MainModule(@NonNull FragmentActivity activity, @NonNull FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @Provides
    public Navigator provideNavigator() {
        return new SupportAppNavigator(
                activity,
                fragmentManager,
                R.id.fragment_container);
    }
}
