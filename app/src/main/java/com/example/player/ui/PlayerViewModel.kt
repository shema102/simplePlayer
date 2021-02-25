package com.example.player.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.player.ui.StreamingPlayer.StreamingPlayer
import com.google.android.exoplayer2.SimpleExoPlayer

class PlayerViewModel(private val streamingPlayer: StreamingPlayer) : ViewModel() {
    fun getPlayer(context: Context): SimpleExoPlayer =
        streamingPlayer.getPlayer(context)

    fun play(url: String, currentPosition: Long, playWhenReady: Boolean) =
        streamingPlayer.play(url, currentPosition, playWhenReady)

    fun releasePlayer() = streamingPlayer.releasePlayer()
}