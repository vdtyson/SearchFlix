package com.versilistyson.searchflix.data.local

import android.content.SharedPreferences
import com.versilistyson.searchflix.domain.entities.Language
import com.versilistyson.searchflix.domain.entities.Region
import com.versilistyson.searchflix.domain.entities.Theme
import javax.inject.Inject

class SharedPrefManager
@Inject constructor(private val sharedPref: SharedPreferences) {
    companion object {

        private enum class SharedPrefKeys(val KEY: String) {
            LANGUAGE_AND_REGION("L&R"),
            DAY_NIGHT_THEME("DNT")
        }

        private const val DEFAULT_LANGUAGE_AND_REGION = "en-US"
        private const val DEFAULT_THEME = 0


    }

    private val sharedPrefEditor by lazy {
        sharedPref.edit()
    }

    fun setLanguageAndRegion(language: Language, region: Region?) {

        var languageAndRegion = language.code

       if(region != null) {
           languageAndRegion = "$languageAndRegion-${region.code}"
       }

        sharedPrefEditor
            .putString(SharedPrefKeys.LANGUAGE_AND_REGION.KEY, languageAndRegion)
            .apply()
    }

    fun fetchLanguageAndRegion() =
        sharedPref.getString(SharedPrefKeys.LANGUAGE_AND_REGION.KEY, DEFAULT_LANGUAGE_AND_REGION)

    fun setTheme(theme: Theme) =
        sharedPrefEditor
            .putInt(SharedPrefKeys.DAY_NIGHT_THEME.KEY, theme.value)
            .apply()

    fun fetchTheme() =
        sharedPref.getInt(SharedPrefKeys.DAY_NIGHT_THEME.KEY, DEFAULT_THEME)

}