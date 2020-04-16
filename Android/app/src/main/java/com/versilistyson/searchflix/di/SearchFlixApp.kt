package com.versilistyson.searchflix.di

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.versilistyson.searchflix.di.component.AppComponent
import com.versilistyson.searchflix.di.component.DaggerAppComponent
import com.versilistyson.searchflix.di.util.InjectorProvider

class SearchFlixApp: Application(), InjectorProvider {

    private val appComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override val component: AppComponent
        get() = appComponent


}
