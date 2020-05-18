package com.versilistyson.searchflix.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.versilistyson.searchflix.data.local.SharedPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private val mainState: MainState by lazy {MainState()}

    val dayNightTheme: LiveData<Int>
        get() = mainState.dayNightTheme


    fun setDayNightTheme(dayNightTheme: Int) {

        viewModelScope.launch(Dispatchers.IO) {

            sharedPrefManager.setTheme(dayNightTheme)
            fetchTheme()
        }
    }

    private fun fetchTheme() {

        viewModelScope.launch(Dispatchers.IO) {

            val savedTheme = async { sharedPrefManager.fetchTheme() }

            withContext(Dispatchers.Main) {
                mainState.dayNightTheme.postValue(savedTheme.await())
            }
        }
    }
}