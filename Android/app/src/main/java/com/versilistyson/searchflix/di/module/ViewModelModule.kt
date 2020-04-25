package com.versilistyson.searchflix.di.module

import androidx.lifecycle.ViewModel
import com.versilistyson.searchflix.di.util.ViewModelKey
import com.versilistyson.searchflix.presentation.dashboard.DashboardViewModel
import com.versilistyson.searchflix.presentation.details.MediaDetailsViewModel
import com.versilistyson.searchflix.presentation.search.MediaSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

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

}