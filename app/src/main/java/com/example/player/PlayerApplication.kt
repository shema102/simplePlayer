package com.example.player

import android.app.Application
import com.example.player.di.component.DaggerAppComponent
import com.example.player.di.module.PlayerModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class PlayerApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var _androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = _androidInjector

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .playerModule(PlayerModule()).build().inject(this)
    }
}