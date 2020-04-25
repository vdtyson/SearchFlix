package com.versilistyson.searchflix.di.component

import android.app.Application
import com.versilistyson.searchflix.data.datasource.search.MovieQueryPagedDataSource
import com.versilistyson.searchflix.data.datasource.search.MovieQueryPagedDataSourceFactory
import com.versilistyson.searchflix.di.module.*
import com.versilistyson.searchflix.presentation.MainActivity
import com.versilistyson.searchflix.presentation.dashboard.DashboardFragment
import com.versilistyson.searchflix.presentation.details.MediaDetailsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        NetworkingModule::class,
        ServiceModule::class,
        AssistedInjectModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }


    fun inject(target: MediaDetailsFragment)
    fun inject(target: MainActivity)
    fun inject(target: DashboardFragment)
}