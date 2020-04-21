package com.versilistyson.searchflix.di.module

import androidx.lifecycle.ViewModel
import com.versilistyson.searchflix.di.util.ViewModelKey
import com.versilistyson.searchflix.presentation.dashboard.DashboardViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(dashboardViewModel: DashboardViewModel): ViewModel

}