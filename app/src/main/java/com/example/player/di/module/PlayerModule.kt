package com.example.player.di.module

import com.example.player.ui.StreamingPlayer.StreamingPlayer
import com.example.player.ui.StreamingPlayer.StreamingPlayerImpl
import dagger.Module
import dagger.Provides

@Module
class PlayerModule {
    @Provides
    fun provideStreamingPlayer(): StreamingPlayer = StreamingPlayerImpl()
}