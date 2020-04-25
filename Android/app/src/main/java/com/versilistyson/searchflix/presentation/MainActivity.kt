package com.versilistyson.searchflix.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.databinding.ActivityMainBinding
import com.versilistyson.searchflix.presentation.common.activity.BaseActivity
import com.versilistyson.searchflix.presentation.common.activity.DataBindingScreen

class MainActivity : BaseActivity(), DataBindingScreen<ActivityMainBinding> {

    override lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var menuItemSearch: MenuItem? = null

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.dashboardFragment,
                R.id.favoritesFragment,
                R.id.settingsFragment
            )
        )
    }

    private val onQueryTextListener: MaterialSearchView.OnQueryTextListener =
        object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // TODO: Not yet implemented
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // TODO: Not yet implemented
                return true
            }

        }


    private val onSearchViewListener: MaterialSearchView.SearchViewListener =
        object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewClosed() {
                // TODO: Not yet implemented
            }

            override fun onSearchViewShown() {
                // TODO: Not yet Implemented
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        onCreateOptionsMenu(binding.toolbar.menu)
    }

    override fun onStart() {
        super.onStart()
        setupNavigation()
        setupSearchView()
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
            when(destination.id) {
                R.id.dashboardFragment -> {
                    binding.toolbar.setMenuItemVisibility(R.id.menu_item_search, true)
                }

                else -> {
                    binding.toolbar.setMenuItemVisibility(R.id.menu_item_search,false)
                }
            }
        }
    }

    private fun MaterialToolbar.setMenuItemVisibility(id: Int, isVisible: Boolean) {
        menu.findItem(id).isVisible = isVisible
    }

    private fun setupBottomNavBar() {
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            menuItem.onNavDestinationSelected(navController)
        }
    }

    private fun setupToolBar() {
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setupSearchView() {
        binding.searchView.setVoiceSearch(true)
        binding.searchView.setOnQueryTextListener(onQueryTextListener)
        binding.searchView.setOnSearchViewListener(onSearchViewListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_dashboard, menu)

        menuItemSearch = menu?.findItem(R.id.menu_item_search)


        menuItemSearch?.let {
            binding.searchView.setMenuItem(menuItemSearch)
        }

        return super.onCreateOptionsMenu(menu)
    }


}
