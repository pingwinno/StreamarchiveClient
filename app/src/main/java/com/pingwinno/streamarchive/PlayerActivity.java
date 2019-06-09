package com.pingwinno.streamarchive;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class PlayerActivity extends AppCompatActivity {
    PlayerView playerView;
    SimpleExoPlayer player;// = ExoPlayerFactory.newSimpleInstance(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        playerView = findViewById(R.id.video_view);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        player = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        playerView.setPlayer(player);
    }

    @Override
    protected void onStart() {
        super.onStart();

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(),
                Util.getUserAgent(getApplicationContext(), "yourApplicationName"));
// This is the MediaSource representing the media to be played.
        MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(getIntent().getStringExtra("url")));
// Prepare the player with the source.
        //player.seekTo(currentWindow, playbackPosition);
        player.prepare(videoSource, true, false);
        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {
                // playbackPosition = player.getContentPosition();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}
