package com.example.player.ui.StreamingPlayer

import android.content.Context
import com.google.android.exoplayer2.SimpleExoPlayer

interface StreamingPlayer {
    fun play(url: String, currentPosition: Long, playWhenReady: Boolean)
    fun getPlayer(context: Context): SimpleExoPlayer
    fun releasePlayer()
}