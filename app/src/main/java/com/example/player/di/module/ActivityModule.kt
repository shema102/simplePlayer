package com.example.player.di.module

import com.example.player.ui.PlayerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun bindPlayerActivity(): PlayerActivity
}