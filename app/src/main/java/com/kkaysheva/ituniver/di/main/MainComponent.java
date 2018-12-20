package com.kkaysheva.ituniver.di.main;

import com.kkaysheva.ituniver.di.scope.ActivityScope;
import com.kkaysheva.ituniver.presentation.main.MainActivity;

import dagger.Subcomponent;

/**
 * MainComponent
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@ActivityScope
@Subcomponent (modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);
}
