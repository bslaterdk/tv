/*
 * Copyright 2017 Martin Kamp Jensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mkjensen.tv.playback;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.source.hls.DefaultHlsDataSourceFactory;
import com.google.android.exoplayer2.source.hls.HlsDataSourceFactory;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.TransferListener;

import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.internal.Version;

@Module
public class PlaybackModule {

  @CheckResult
  @NonNull
  @Provides
  // Not a singleton because the player cannot be used after release() has been called.
  SimpleExoPlayer exoPlayer(@NonNull RenderersFactory renderersFactory,
                            @NonNull LoadControl loadControl, @NonNull TrackSelector trackSelector) {

    return ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
  }

  @CheckResult
  @NonNull
  @Provides
  @Singleton
  BandwidthMeter exoPlayerBandwidthMeter(@NonNull DefaultBandwidthMeter defaultBandwidthMeter) {

    return defaultBandwidthMeter;
  }

  @CheckResult
  @NonNull
  @Provides
  @Singleton
  DataSource.Factory exoPlayerDataSourceFactory(@NonNull OkHttpClient okHttpClient,
                                                @NonNull TransferListener<? super DataSource> transferListener) {

    return new OkHttpDataSourceFactory(okHttpClient, Version.userAgent(), transferListener);
  }

  @CheckResult
  @NonNull
  @Provides
  @Singleton
  DefaultBandwidthMeter exoPlayerDefaultBandwidthMeter() {

    return new DefaultBandwidthMeter.Builder()
        .setInitialBitrateEstimate(10 * 1024 * 1024)
        .build();
  }

  @CheckResult
  @NonNull
  @Provides
  @Singleton
  HlsDataSourceFactory exoPlayerHlsDataSourceFactory(DataSource.Factory dataSourceFactory) {

    return new DefaultHlsDataSourceFactory(dataSourceFactory);
  }

  @CheckResult
  @NonNull
  @Provides
  @Singleton
  HlsMediaSource.Factory exoPlayerHlsMediaSourceFactory(HlsDataSourceFactory hlsDataSourceFactory) {

    return new HlsMediaSource.Factory(hlsDataSourceFactory);
  }

  @CheckResult
  @NonNull
  @Provides
  @Singleton
  LoadControl exoPlayerLoadControl() {

    return new DefaultLoadControl();
  }

  @CheckResult
  @NonNull
  @Provides
  @Singleton
  RenderersFactory exoPlayerRenderersFactory(@NonNull Context context) {

    return new DefaultRenderersFactory(context);
  }

  @CheckResult
  @NonNull
  @Provides
  @Singleton
  TrackSelector exoPlayerTrackSelector(TrackSelection.Factory trackSelectionFactory) {

    return new DefaultTrackSelector(trackSelectionFactory);
  }

  @CheckResult
  @NonNull
  @Provides
  @Singleton
  TrackSelection.Factory exoPlayerTrackSelectionFactory(BandwidthMeter bandwidthMeter) {

    return new AdaptiveTrackSelection.Factory(bandwidthMeter);
  }

  @CheckResult
  @NonNull
  @Provides
  @Singleton
  TransferListener<? super DataSource> exoPlayerTransferListener(@NonNull DefaultBandwidthMeter defaultBandwidthMeter) {

    return defaultBandwidthMeter;
  }
}
