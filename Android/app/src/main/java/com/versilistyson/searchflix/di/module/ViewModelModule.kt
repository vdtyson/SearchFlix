package com.versilistyson.searchflix.di.module

import androidx.lifecycle.ViewModel
import com.versilistyson.searchflix.di.util.ViewModelKey
import com.versilistyson.searchflix.presentation.ui.MainViewModel
import com.versilistyson.searchflix.presentation.ui.dashboard.DashboardViewModel
import com.versilistyson.searchflix.presentation.ui.details.MediaDetailsViewModel
import com.versilistyson.searchflix.presentation.ui.favorites.FavoritesViewModel
import com.versilistyson.searchflix.presentation.ui.search.MediaSearchViewModel
import com.versilistyson.searchflix.presentation.ui.settings.LanguageAndRegionViewModel
import com.versilistyson.searchflix.presentation.ui.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(dashboardViewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MediaDetailsViewModel::class)
    abstract fun bindMediaDetailsViewModel(mediaDetailsViewModel: MediaDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MediaSearchViewModel::class)
    abstract fun bindMediaSearchViewModel(mediaSearchViewModel: MediaSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(favoritesViewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LanguageAndRegionViewModel::class)
    abstract fun bindLanguageAndRegionViewModel(languageAndRegionViewModel: LanguageAndRegionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}