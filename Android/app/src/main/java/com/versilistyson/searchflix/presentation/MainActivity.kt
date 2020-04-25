package com.versilistyson.searchflix.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import br.com.mauker.materialsearchview.MaterialSearchView
import com.google.android.material.appbar.MaterialToolbar
import com.versilistyson.searchflix.NavGraphMainDirections
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.databinding.ActivityMainBinding
import com.versilistyson.searchflix.domain.entities.MediaType
import com.versilistyson.searchflix.presentation.common.activity.BaseActivity
import com.versilistyson.searchflix.presentation.common.activity.DataBindingScreen
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), DataBindingScreen<ActivityMainBinding> {

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

                if(!query.isNullOrBlank()) {
                    val toSearchFragment =
                        NavGraphMainDirections.actionGlobalMediaSearchFragment(query, MediaType.MOVIE)

                    navController.navigate(toSearchFragment)
                }


                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //TODO
                return true
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
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
            when (destination.id) {
                R.id.dashboardFragment -> {
                    binding.toolbar.setMenuItemVisibility(R.id.menu_item_search, true)
                }

                else -> {
                    binding.toolbar.setMenuItemVisibility(R.id.menu_item_search, false)
                    if(binding.searchView.isOpen) binding.searchView.closeSearch()
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

        binding.toolbar.inflateMenu(R.menu.menu_toolbar_dashboard)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        binding.toolbar.setOnMenuItemClickListener(
            Toolbar.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.menu_item_search -> {
                        binding.searchView.openSearch()
                    }
                }
                return@OnMenuItemClickListener true
            }
        )
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
