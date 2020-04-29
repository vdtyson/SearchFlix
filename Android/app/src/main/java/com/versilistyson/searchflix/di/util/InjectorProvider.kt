package com.versilistyson.searchflix.di.util

import com.versilistyson.searchflix.di.component.AppComponent
import kotlinx.coroutines.InternalCoroutinesApi

interface InjectorProvider {
    @InternalCoroutinesApi
    val component: AppComponent
}