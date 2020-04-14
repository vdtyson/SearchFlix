package com.versilistyson.searchflix.di.util

import android.app.Activity
import androidx.fragment.app.Fragment
import com.versilistyson.searchflix.di.SearchFlixApp

val Activity.injector get() = (application as SearchFlixApp).component
val Fragment.activityInjector get() = requireActivity().injector