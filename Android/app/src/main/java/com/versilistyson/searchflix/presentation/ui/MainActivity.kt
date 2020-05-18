package com.versilistyson.searchflix.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import br.com.mauker.materialsearchview.MaterialSearchView
import com.versilistyson.searchflix.NavGraphMainDirections
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.databinding.ActivityMainBinding
import com.versilistyson.searchflix.di.util.DaggerViewModelFactory
import com.versilistyson.searchflix.di.util.injector
import com.versilistyson.searchflix.domain.entities.MediaType
import com.versilistyson.searchflix.presentation.ui.common.activity.BaseActivity
import com.versilistyson.searchflix.presentation.ui.common.activity.DataBindingScreen
import com.versilistyson.searchflix.presentation.util.clearMenu
import com.versilistyson.searchflix.presentation.util.setMenuItemVisibility
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

class MainActivity : BaseActivity(), DataBindingScreen<ActivityMainBinding> {

    @Inject lateinit var daggerViewModelFactory: DaggerViewModelFactory

    private val viewModel: MainViewModel by viewModels{
        daggerViewModelFactory
    }

    override lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.dashboardFragment,
                R.id.favoritesFragment,
                R.id.settingsFragment
            )
        )
    }

    private val onQueryTextListener =
        object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (!query.isNullOrBlank()) {
                    val toSearchFragment =
                        NavGraphMainDirections.actionGlobalMediaSearchFragment(
                            query,
                            MediaType.MOVIE
                        )

                    navController.navigate(toSearchFragment)
                }


                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //TODO
                return true
            }

        }

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        setupNavigation()
        setupSearchView()
        viewModel.dayNightTheme.observe(this, Observer { renderDayNightIcon(it) })
    }

    private fun renderDayNightIcon(currentTheme: Int) {
        val dayNightMenuItem = binding.toolbar.menu.findItem(R.id.menu_item_daynight)
        when(currentTheme) {
            AppCompatDelegate.MODE_NIGHT_YES -> dayNightMenuItem.isChecked = true
            else -> dayNightMenuItem.isChecked = false
        }
    }

    private fun setupNavigation() {
        navController = binding.navHostFragmentContainer.findNavController()
        navController.setGraph(R.navigation.nav_graph_main)
        setupBottomNavBar()
        setupToolBar()
        setupOnDestinationChangedListener()
    }

    private fun setupOnDestinationChangedListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.dashboardFragment -> {
                    menuItemClickListener()
                    binding.toolbar.setMenuItemVisibility(R.id.menu_item_daynight, true)
                    binding.toolbar.setMenuItemVisibility(R.id.menu_item_favorite, false)
                    binding.toolbar.setMenuItemVisibility(R.id.menu_item_search, true)
                }

                R.id.mediaDetailsFragment  -> {
                    binding.toolbar.setMenuItemVisibility(R.id.menu_item_daynight, false)
                    binding.toolbar.setMenuItemVisibility(R.id.menu_item_search, false)
                    binding.toolbar.setMenuItemVisibility(R.id.menu_item_favorite, true)
                    if (binding.searchView.isOpen) binding.searchView.closeSearch()
                }

                R.id.summaryDialogFragment -> {
                    binding.toolbar.setMenuItemVisibility(R.id.menu_item_daynight, false)
                }

                else -> {
                    menuItemClickListener()
                    binding.toolbar.setMenuItemVisibility(R.id.menu_item_daynight, true)
                    binding.toolbar.setMenuItemVisibility(R.id.menu_item_favorite, false)
                    binding.toolbar.setMenuItemVisibility(R.id.menu_item_search, false)
                    if(binding.searchView.isOpen) binding.searchView.closeSearch()
                }
            }
        }
    }



    private fun setupBottomNavBar() {
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            menuItem.onNavDestinationSelected(navController)
        }
    }

    private fun menuItemClickListener() {
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {

                R.id.menu_item_search -> {
                    binding.searchView.openSearch()
                }

                R.id.menu_item_daynight -> {

                    when {
                        item.isChecked -> {
                            viewModel.setDayNightTheme(AppCompatDelegate.MODE_NIGHT_NO)
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        }

                        else -> {
                            viewModel.setDayNightTheme(AppCompatDelegate.MODE_NIGHT_YES)
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        }
                    }
                }
            }
            true
        }
    }
    private fun setupToolBar() {
        binding.toolbar.menu.clearMenu()
        binding.toolbar.inflateMenu(R.menu.menu_toolbar_dashboard)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)


    }

    override fun onBackPressed() {
        when {

            binding.searchView.isOpen -> binding.searchView.closeSearch()

            else -> super.onBackPressed()
        }
    }

    private fun setupSearchView() {

        binding.searchView.setOnQueryTextListener(onQueryTextListener)
    }

}
