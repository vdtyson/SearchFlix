package com.versilistyson.searchflix.presentation

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.databinding.ActivityMainBinding
import com.versilistyson.searchflix.di.util.injector
import com.versilistyson.searchflix.presentation.common.activity.BaseActivity
import com.versilistyson.searchflix.presentation.common.activity.DataBindingScreen

class MainActivity : BaseActivity(), DataBindingScreen<ActivityMainBinding> {

    override lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        setupNavigation()
    }

    private fun setupNavigation() {
        navController = binding.navHostFragmentContainer.findNavController()
        navController.setGraph(R.navigation.nav_graph_main)
        setupToolbar()
        setupBottomNavBar()
    }

    private fun setupToolbar() {

        val topLevelDestinations = setOf(
            R.id.dashboardFragment,
            R.id.favoritesFragment,
            R.id.settingsFragment
        )
        val appBarConfigruation = AppBarConfiguration(topLevelDestinations)
        binding.toolbar.setupWithNavController(navController, appBarConfigruation)
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            menuItem.onNavDestinationSelected(navController)
        }
    }
    private fun setupBottomNavBar() {
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            menuItem.onNavDestinationSelected(navController)
        }
    }




}
