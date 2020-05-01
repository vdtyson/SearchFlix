package com.versilistyson.searchflix.presentation.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.versilistyson.searchflix.data.local.SharedPrefManager
import com.versilistyson.searchflix.domain.entities.Language
import com.versilistyson.searchflix.domain.entities.Region
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LanguageAndRegionViewModel
@Inject constructor(private val prefManager: SharedPrefManager) : ViewModel() {

    fun changeLanguageAndRegion(language: Language, region: Region?) {
        viewModelScope.launch(Dispatchers.IO) {
            prefManager.setLanguageAndRegion(language, region)
        }
    }
}