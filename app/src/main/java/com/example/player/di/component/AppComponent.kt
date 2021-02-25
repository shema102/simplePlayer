package com.example.player.di.component

import android.app.Application
import com.example.player.PlayerApplication
import com.example.player.di.module.ActivityModule
import com.example.player.di.module.PlayerModule
import com.example.player.ui.PlayerActivity
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        PlayerModule::class,
        ActivityModule::class]
)
interface AppComponent : AndroidInjector<Application> {
    fun inject(playerApplication: PlayerApplication)

    fun inject(playerActivity: PlayerActivity)
}