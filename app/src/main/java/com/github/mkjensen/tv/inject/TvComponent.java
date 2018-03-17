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

package com.github.mkjensen.tv.inject;

import com.github.mkjensen.tv.TvApplication;
import com.github.mkjensen.tv.backend.BackendModule;
import com.github.mkjensen.tv.live.LiveModule;
import com.github.mkjensen.tv.playback.PlaybackModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
    AndroidSupportInjectionModule.class,
    ApplicationModule.class,
    BackendModule.class,
    LiveModule.class,
    PlaybackModule.class,
})
@Singleton
interface TvComponent extends AndroidInjector<TvApplication> {

  @Component.Builder
  abstract class Builder extends AndroidInjector.Builder<TvApplication> {
  }
}
