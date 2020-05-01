package com.versilistyson.searchflix.presentation.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.databinding.FragmentLanguageRegionBinding
import com.versilistyson.searchflix.di.util.DaggerViewModelFactory
import com.versilistyson.searchflix.di.util.activityInjector
import com.versilistyson.searchflix.domain.entities.Language
import com.versilistyson.searchflix.domain.entities.Region
import com.versilistyson.searchflix.presentation.ui.common.fragment.BindedFragment
import com.versilistyson.searchflix.presentation.util.showToast
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject


class LanguageAndRegionSettingsFragment : BindedFragment<FragmentLanguageRegionBinding>() {

    override val layoutId: Int = R.layout.fragment_language_region

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    private val viewmodel: LanguageAndRegionViewModel by viewModels {
        daggerViewModelFactory
    }

    var languageAdapter: ArrayAdapter<CharSequence>? = null
    var spanishRegionAdapter: ArrayAdapter<CharSequence>? = null
    var englishRegionAdaper: ArrayAdapter<CharSequence>? = null

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        activityInjector.inject(this)
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinners()
        setupSaveButton()
    }

    private fun setupSaveButton() {
        binding.bttnSaveLanguageAndRegion.setOnClickListener {
            val languageText = binding.spinnerLanguage.selectedItem.toString()
            val regionText = binding.spinnerRegion.selectedItem.toString()
            var region: Region? = null

            val language = when(languageText) {
                "English" -> {
                    Language.ENGLISH
                }

                "Spanish" -> {
                    Language.SPANISH

                }

                else -> {
                    Language.ENGLISH
                }
            }

            if(regionText != "All Regions") {
                when(regionText) {
                    "Mexico" -> {
                        region = Region.MEXICO
                    }

                    "Spain" -> {
                        region = Region.SPAIN
                    }

                    "Colombia" -> {
                        region = Region.COLOMBIA
                    }

                    "Argentina" -> {
                        region = Region.ARGENTINA
                    }

                    "United States" -> {
                        region = Region.UNITED_STATES
                    }

                    "United Kingdom" -> {
                        region = Region.UNITED_KINGDOM
                    }
                }
            }
            showToast("language: ${language.code} region: ${region?.code ?: "any"}")
            viewmodel.changeLanguageAndRegion(language, region)

        }
    }

    private fun setupSpinners() {

        initSpanishRegionAdapter()
        initEnglishRegionAdapter()
        initLanguageSpinnerAdapter()

        binding.spinnerLanguage.adapter = languageAdapter
        binding.spinnerLanguage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.spinnerRegion.visibility = View.GONE
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    binding.spinnerRegion.visibility = View.VISIBLE

                    when (parent?.getItemAtPosition(position).toString()) {
                        "English" -> {
                            binding.spinnerRegion.adapter = englishRegionAdaper
                        }

                        "Spanish" -> {
                            binding.spinnerRegion.adapter = spanishRegionAdapter
                        }
                    }
                }

            }
    }

    private fun initLanguageSpinnerAdapter() {
        languageAdapter = context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.language_array,
                android.R.layout.simple_spinner_item
            )
        }
    }

    private fun initEnglishRegionAdapter() {
        englishRegionAdaper = context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.english_region_array,
                android.R.layout.simple_spinner_item
            )
        }
    }

    private fun initSpanishRegionAdapter() {
        spanishRegionAdapter = context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.spanish_region_array,
                android.R.layout.simple_spinner_item
            )
        }
    }


}
