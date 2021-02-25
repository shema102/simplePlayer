package com.example.player.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.player.ui.PlayerViewModel
import com.example.player.ui.StreamingPlayer.StreamingPlayer
import javax.inject.Inject

class PlayerViewModelFactory @Inject constructor(private val streamingPlayer: StreamingPlayer) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlayerViewModel(streamingPlayer) as T
    }
}