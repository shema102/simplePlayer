package com.example.player.ui

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.*
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.player.R
import com.example.player.databinding.PlayerActivityBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: PlayerActivityBinding
    private var player: SimpleExoPlayer? = null
    private lateinit var mediaUrl: String

    private lateinit var fullscreenButton: ImageView

    private var currentPosition: Long = 0
    private var playWhenReady: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PlayerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fullscreenButton = binding.playerWindow.findViewById(R.id.exo_fullscreen_icon)

        mediaUrl = this.getString(R.string.data_uri)

    }

    override fun onStart() {
        super.onStart()
        initUi()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    override fun onResume() {
        super.onResume()
        initializePlayer()
    }

    override fun onPause() {
        super.onPause()
        log("On pause")
    }

    private fun initUi() {
        fullscreenButton.setOnClickListener { changeOrientation() }

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideStatusBarDelayed(2000L)
            fullscreenButton.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_baseline_fullscreen_exit_24,
                    null
                )
            )
        } else {
            fullscreenButton.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_baseline_fullscreen_24,
                    null
                )
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        player?.let {
            outState.putLong("CURRENT_PLAYBACK_POSITION", it.contentPosition)
            outState.putBoolean("IS_PLAYING_BACK", it.isPlaying)
            log("Saved Instance")
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentPosition = savedInstanceState.getLong("CURRENT_PLAYBACK_POSITION")
        playWhenReady = savedInstanceState.getBoolean("IS_PLAYING_BACK")
        log("Restore Instance; position: $currentPosition, playback: $playWhenReady")
    }

    private fun initializePlayer() {
        GlobalScope.launch(Dispatchers.Main) {
            player = SimpleExoPlayer.Builder(this@PlayerActivity).build()
            binding.playerWindow.player = player
            binding.playerWindow.controllerShowTimeoutMs = 3000
            val uri = Uri.parse(mediaUrl)
            val mediaSource: MediaSource = buildMediaSource(uri)
            if (player != null) {
                player?.seekTo(player!!.currentWindowIndex, currentPosition)
                player?.setMediaSource(mediaSource, false)
                player?.playWhenReady = playWhenReady
                player?.prepare()
            }
            log("Init Player")
        }
    }

    private fun releasePlayer() {
        player?.release()
        player = null
        log("Release Player")
    }

    private fun buildMediaSource(mediaUri: Uri): HlsMediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, "exoplayer-codelab")
        val mediaItem = MediaItem.fromUri(mediaUri)
        return HlsMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
    }

    private fun changeOrientation() {
        val orientation = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        } else {
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    private fun hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
        log("Hide status bar")
    }

    private fun hideStatusBarDelayed(delayMs: Long) {
        Handler(Looper.getMainLooper()).postDelayed({
            hideStatusBar()
        }, delayMs)
    }

    private fun log(string: String) {
        Log.d("PlayerActivity", string)
    }
}
