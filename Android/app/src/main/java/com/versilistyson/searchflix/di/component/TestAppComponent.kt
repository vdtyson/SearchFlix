package com.versilistyson.searchflix.di.component

import com.versilistyson.searchflix.di.module.AppModule
import com.versilistyson.searchflix.di.module.TestAppModule
import com.versilistyson.searchflix.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,TestAppModule::class,ViewModelModule::class])
interface TestAppComponent {
}