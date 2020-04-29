package com.versilistyson.searchflix.di.util

import android.app.Activity
import androidx.fragment.app.Fragment
import com.versilistyson.searchflix.di.SearchFlixApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
val Activity.injector get() = (application as SearchFlixApp).component
@InternalCoroutinesApi
val Fragment.activityInjector get() = requireActivity().injector