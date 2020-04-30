package com.versilistyson.searchflix.presentation.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.versilistyson.searchflix.data.local.SharedPrefManager
import com.versilistyson.searchflix.domain.entities.Language
import com.versilistyson.searchflix.domain.entities.Region
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsViewModel
@Inject constructor(private val prefManager: SharedPrefManager) : ViewModel() {

    private val settingsState by lazy {
        SettingsState()
    }
    val currentLanguageAndRegion: LiveData<String>
        get() = settingsState.currentLanguageAndRegionComponent

    private fun getCurrentRegion() {
        viewModelScope.launch(Dispatchers.Main) {
            val languageAndRegion = async(Dispatchers.IO) {
                prefManager.fetchLanguageAndRegion() ?: "en-US"
            }
            settingsState.currentLanguageAndRegionComponent.postValue(languageAndRegion.await())
        }
    }

    private fun changeLanguageAndRegion(language: Language, region: Region? = null) {

        viewModelScope.launch(Dispatchers.IO) {
            prefManager.setLanguageAndRegion(language,region)
        }.invokeOnCompletion {
            getCurrentRegion()
        }

    }
}