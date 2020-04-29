package com.versilistyson.searchflix.di.component

import android.app.Application
import com.versilistyson.searchflix.di.module.*
import com.versilistyson.searchflix.presentation.MainActivity
import com.versilistyson.searchflix.presentation.dashboard.DashboardFragment
import com.versilistyson.searchflix.presentation.details.MediaDetailsFragment
import com.versilistyson.searchflix.presentation.favorites.FavoritesFragment
import com.versilistyson.searchflix.presentation.search.MediaSearchFragment
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        NetworkingModule::class,
        ServiceModule::class,
        AssistedInjectModule::class,
        RoomModule::class
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
    fun inject(target: MediaSearchFragment)
    fun inject(target: FavoritesFragment)
}