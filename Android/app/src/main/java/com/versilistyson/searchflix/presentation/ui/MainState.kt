package com.versilistyson.searchflix.presentation.ui

import androidx.lifecycle.MutableLiveData
import com.versilistyson.searchflix.presentation.ui.common.UIState

class MainState: UIState {
    val dayNightTheme: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}