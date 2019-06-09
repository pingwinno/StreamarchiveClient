package com.pingwinno.streamarchive;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.rubensousa.previewseekbar.PreviewLoader;
import com.github.rubensousa.previewseekbar.PreviewView;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBar;
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

import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener,
        PreviewView.OnPreviewChangeListener {
    PlayerView playerView;
    PreviewTimeBar previewTimeBar;
    SimpleExoPlayer player;// = ExoPlayerFactory.newSimpleInstance(context);
    PreviewLoader loader;
    ImageView imageView;
    StreamMeta streamMeta;
    SharedPreferences appPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        playerView = findViewById(R.id.video_view);
        imageView = findViewById(R.id.imageView);
        previewTimeBar = playerView.findViewById(R.id.exo_progress);
        previewTimeBar.addOnPreviewChangeListener(this);
        appPref = getSharedPreferences("SavedTime", Context.MODE_PRIVATE);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        player = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        playerView.setPlayer(player);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        streamMeta = (StreamMeta) getIntent().getSerializableExtra("stream");
        for (Preview preview : streamMeta.getTimelinePreviews().values()) {
            Log.e("PREVIEW", preview.getSrc());
            Glide.with(getApplicationContext())
                    .load("https://storage.streamarchive.net/streams/" +
                            "olyashaa/07e29347-222d-4fcc-a926-9e91673aad67/timeline_preview/" + preview.getSrc())
                    .preload();
        }
        loader = new PreviewLoader() {
            @Override
            public void loadPreview(long currentPosition, long max) {
                player.setPlayWhenReady(false);
                Log.e("TIME", String.valueOf(currentPosition));
                Log.e("PREVIEW", streamMeta.getTimelinePreviews().floorEntry(TimeUnit.MILLISECONDS.toSeconds(currentPosition) + 10).getValue()
                        .getSrc());
                GlideApp.with(getApplicationContext())
                        .load("https://storage.streamarchive.net/streams/" +
                                "olyashaa/07e29347-222d-4fcc-a926-9e91673aad67/timeline_preview/" +
                                streamMeta.getTimelinePreviews().floorEntry(TimeUnit.MILLISECONDS.toSeconds(currentPosition) + 10).getValue()
                                        .getSrc())
                        .onlyRetrieveFromCache(true)
                        .into(imageView);
            }
        };


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        playerView.showController();
    }

    @Override
    protected void onStart() {
        super.onStart();

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(),
                Util.getUserAgent(getApplicationContext(), "StreamArchive"));
// This is the MediaSource representing the media to be played.
        MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse("https://storage.streamarchive.net/streams/olyashaa/" + streamMeta.getUuid() + "/index-dvr.m3u8"));
// Prepare the player with the source.

        Log.e("LoadedTime", appPref.getLong(streamMeta.getUuid(), 0) + "");
        player.prepare(videoSource, true, false);
        previewTimeBar.setPreviewLoader(loader);
        player.setPlayWhenReady(true);

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
                switch (playbackState) {
                    case Player.STATE_READY:
                        findViewById(R.id.loading).setVisibility(View.GONE);

                        break;
                    case Player.STATE_BUFFERING:
                        findViewById(R.id.loading).setVisibility(View.VISIBLE);
                        break;
                }
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
                player.setPlayWhenReady(true);
                Log.e("POSITION", String.valueOf(player.getContentPosition()));
            }
        });
        player.seekTo(appPref.getLong(streamMeta.getUuid(), 0));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("SavedTime", player.getContentPosition() + "");
        SharedPreferences.Editor editor = appPref.edit();
        editor.putLong(streamMeta.getUuid(), player.getContentPosition());
        editor.apply();
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    @Override
    public void onStartPreview(PreviewView previewView, int progress) {
    }

    @Override
    public void onStopPreview(PreviewView previewView, int progress) {
    }

    @Override
    public void onPreview(PreviewView previewView, int progress, boolean fromUser) {

    }
}
