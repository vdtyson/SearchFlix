package com.versilistyson.searchflix.presentation.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.databinding.FragmentSettingsBinding
import com.versilistyson.searchflix.di.util.DaggerViewModelFactory
import com.versilistyson.searchflix.di.util.activityInjector
import com.versilistyson.searchflix.presentation.ui.common.activity.DataBindingScreen
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment(), DataBindingScreen<FragmentSettingsBinding> {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    private val viewModel: SettingsViewModel by viewModels {
        daggerViewModelFactory
    }

    override lateinit var binding: FragmentSettingsBinding

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        activityInjector.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_settings, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated { viewModel.getCurrentRegion() }
        setupListeners()
        viewModel.currentLanguageAndRegion.observe(
            viewLifecycleOwner,
            Observer(::render)
        )
    }

    private fun render(currentLanguageAndRegion: String) {
        binding.tvCurrentLanguage.text = currentLanguageAndRegion
    }

    private fun setupListeners() {
        binding.bttnLanguageAndRegion.setOnClickListener {

            val toSettingsAndRegionsSettingsFragment = SettingsFragmentDirections.actionSettingsFragmentToLanguageAndRegionSettingsFragment()

            findNavController().navigate(toSettingsAndRegionsSettingsFragment)
        }
    }
}
