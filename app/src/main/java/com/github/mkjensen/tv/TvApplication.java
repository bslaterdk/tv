/*
 * Copyright 2016 Martin Kamp Jensen
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

package com.github.mkjensen.tv;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.github.mkjensen.tv.inject.DaggerTvComponent;
import com.github.mkjensen.tv.util.CrashlyticsTimberTree;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class TvApplication extends DaggerApplication {

  @Override
  public void onCreate() {

    initializeCrashlytics();

    initializeTimber();

    super.onCreate();
  }

  @Override
  protected AndroidInjector<TvApplication> applicationInjector() {

    return DaggerTvComponent.factory().create(this);
  }

  private void initializeCrashlytics() {

    CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder()
        .disabled(BuildConfig.DEBUG)
        .build();

    Crashlytics crashlytics = new Crashlytics.Builder()
        .core(crashlyticsCore)
        .build();

    Fabric.with(this, crashlytics);
  }

  private static void initializeTimber() {

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
      Timber.plant(new CrashlyticsTimberTree());
    }
  }
}
