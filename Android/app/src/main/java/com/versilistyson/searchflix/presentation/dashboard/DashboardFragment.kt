package com.versilistyson.searchflix.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.databinding.FragmentDashboardBinding
import com.versilistyson.searchflix.di.util.DaggerViewModelFactory
import com.versilistyson.searchflix.di.util.activityInjector
import com.versilistyson.searchflix.domain.entities.Category
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.presentation.adapters.CategoryAdapter
import com.versilistyson.searchflix.presentation.common.activity.DataBindingScreen
import javax.inject.Inject


class DashboardFragment : Fragment(), DataBindingScreen<FragmentDashboardBinding> {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory
    private val viewModel: DashboardViewModel by viewModels {
        daggerViewModelFactory
    }

    private lateinit var categoryAdapter: CategoryAdapter

    private val popularMoviesCategory by lazy { Category("Popular Movies") }
    private val topRatedMoviesCategory by lazy { Category("Top Rated Movies") }
    private val upcomingMoviesCategory by lazy { Category("Upcoming Movies") }
    private val categoryList by lazy {
        listOf(
            popularMoviesCategory,
            topRatedMoviesCategory,
            upcomingMoviesCategory
        )
    }

    override lateinit var binding: FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        activityInjector.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderState()
        setFetchersForCategories()
        setupRecyclerView()
    }



    private fun renderState() {
        viewModel.dashboardState.observe(
            viewLifecycleOwner,
            Observer { latestState ->
                popularMoviesCategory.onStateChanged(latestState.popularMoviesComponent)
                upcomingMoviesCategory.onStateChanged(latestState.upcomingMoviesComponent)
                topRatedMoviesCategory.onStateChanged(latestState.topRatedMoviesComponent)
            }
        )
    }

    private fun Category.onStateChanged(state: MediaListStateComponent) {
        when(state) {
            is MediaListStateComponent.Loading -> Toast.makeText(context, "${topRatedMoviesCategory.title} is loading", Toast.LENGTH_SHORT).show()
            is MediaListStateComponent.Error -> Toast.makeText(context, "${this.title} failure: ${state.failure}", Toast.LENGTH_LONG).show()
            is MediaListStateComponent.Data -> this.updateMediaList(state.value)
        }
    }

    private fun setFetchersForCategories() {
        popularMoviesCategory.fetcherFn = { viewModel.getPopularMovies() }
        topRatedMoviesCategory.fetcherFn = { viewModel.getTopRatedMovies() }
        upcomingMoviesCategory.fetcherFn = { viewModel.getUpcomingMovies() }
    }

    private fun setupRecyclerView() {

        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        categoryAdapter = CategoryAdapter(
            viewLifecycleOwner,
            categoryList,
            onCategoryTitleClickListener
        ) { media ->
            onMediaItemClick(media)
        }

        binding.categoryRecyclerView.layoutManager = linearLayoutManager
        binding.categoryRecyclerView.adapter = categoryAdapter
    }

    private val onCategoryTitleClickListener =
        View.OnClickListener {
            Toast.makeText(this.context, "Title Clicked", Toast.LENGTH_SHORT).show()
        }

    private fun onMediaItemClick(media: Media) {

        val toMediaDetailsFragment =
            DashboardFragmentDirections.actionDashboardFragmentToMediaDetailsFragment(media)

        findNavController().navigate(toMediaDetailsFragment)
    }
}
