package com.versilistyson.searchflix.di.component

import android.app.Application
import com.versilistyson.searchflix.di.module.AppModule
import com.versilistyson.searchflix.di.module.ViewModelModule
import com.versilistyson.searchflix.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application) : AppComponent
    }

    fun inject(target: MainActivity)
}