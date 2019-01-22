package com.kkaysheva.ituniver.di.app;

import com.kkaysheva.ituniver.di.main.MainModule;
import com.kkaysheva.ituniver.presentation.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityBuilderModule {

    @ContributesAndroidInjector(modules = {MainModule.class})
    MainActivity bindMainActivity();
}
