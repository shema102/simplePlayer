package com.example.player.ui.StreamingPlayer

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class StreamingPlayerImpl : StreamingPlayer {

    private lateinit var context: Context
    private lateinit var player: SimpleExoPlayer

    override fun play(url: String, currentPosition: Long, playWhenReady: Boolean) {
        val uri = Uri.parse(url)
        val mediaSource: MediaSource = buildMediaSource(uri)
        player.setMediaSource(mediaSource, false)
        player.seekTo(player.currentWindowIndex, currentPosition)
        player.playWhenReady = playWhenReady
        player.prepare()
    }


    override fun getPlayer(context: Context): SimpleExoPlayer {
        this.context = context
        initializePlayer()
        return player
    }

    override fun releasePlayer() {
        player.stop()
        player.release()
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this@StreamingPlayerImpl.context).build()
        log("Init Player")

    }

    private fun buildMediaSource(mediaUri: Uri): HlsMediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this.context, "exoplayer-codelab")
        val mediaItem = MediaItem.fromUri(mediaUri)
        return HlsMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
    }

    private fun log(string: String) {
        Log.d("StreamingPlayerImpl", string)
    }
}