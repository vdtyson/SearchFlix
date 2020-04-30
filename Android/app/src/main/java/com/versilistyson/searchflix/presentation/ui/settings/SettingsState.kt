package com.versilistyson.searchflix.presentation.ui.settings

import androidx.lifecycle.MutableLiveData
import com.versilistyson.searchflix.presentation.ui.common.UIState

class SettingsState(): UIState {
    val currentLanguageAndRegionComponent: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}