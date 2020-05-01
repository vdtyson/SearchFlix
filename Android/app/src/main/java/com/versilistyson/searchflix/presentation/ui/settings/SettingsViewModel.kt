package com.versilistyson.searchflix.presentation.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.versilistyson.searchflix.data.local.SharedPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel
@Inject constructor(private val prefManager: SharedPrefManager): ViewModel() {
    private val settingsState by lazy {
        SettingsState()
    }

    fun getCurrentRegion() {
        viewModelScope.launch(Dispatchers.Main) {
            val languageAndRegion = async(Dispatchers.IO) {
                prefManager.fetchLanguageAndRegion() ?: "en-US"
            }
            settingsState.currentLanguageAndRegionComponent.postValue(languageAndRegion.await())
        }
    }

    val currentLanguageAndRegion: LiveData<String>
    get() = settingsState.currentLanguageAndRegionComponent
}