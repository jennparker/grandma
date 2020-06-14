package com.booisajerk.grandma

import android.os.Bundle
import android.util.Log
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        youtube_view.initialize(Config.YOUTUBE_API_KEY, this)
    }


    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        playerRestored: Boolean
    ) {
        Log.d(
            TAG,
            "onInitializationSuccess, player: $player, provider: $provider, restored: $playerRestored"
        )
        player?.setPlayerStateChangeListener(playerStateChangeListener)
        player?.setPlaybackEventListener(playbackEventListener)
        if (!playerRestored) {
            player?.cueVideo(Config.YOUTUBE_VIDEO_ID)
            player?.play()
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult
    ) {
        if (youTubeInitializationResult?.isUserRecoverableError) {
            youTubeInitializationResult?.getErrorDialog(this, REQUEST_CODE).show()

        } else {
            Log.e(
                TAG, "Error initializing player: $youTubeInitializationResult"
            )
        }
    }

    private val playbackEventListener = object : YouTubePlayer.PlaybackEventListener {
        override fun onSeekTo(location: Int) {
            Log.d(TAG, "onSeekTo: $location")
        }

        override fun onBuffering(isBuffering: Boolean) {
            Log.d(TAG, "onBuffering $isBuffering")
        }

        override fun onPaused() {
            Log.d(TAG, "onPaused")

        }

        override fun onPlaying() {
            Log.d(TAG, "onPlaying")
        }

        override fun onStopped() {
            Log.d(TAG, "onStopped")
        }
    }

    private val playerStateChangeListener = object : YouTubePlayer.PlayerStateChangeListener {

        override fun onAdStarted() {
            Log.d(TAG, "onAdStarted")
        }

        override fun onLoading() {
            Log.d(TAG, "onLoading")
        }

        override fun onLoaded(videoId: String?) {
            Log.d(TAG, "onLoaded: $videoId")
        }

        override fun onVideoStarted() {
            Log.d(TAG, "onVideoStarted")
        }

        override fun onVideoEnded() {
            Log.d(TAG, "onVideoEnded")
        }

        override fun onError(errorReason: YouTubePlayer.ErrorReason?) {
            Log.d(TAG, "onError reason: $errorReason")
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_CODE = 0
    }
}