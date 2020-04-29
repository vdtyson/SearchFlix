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
import com.versilistyson.searchflix.presentation.util.provideLinearLayoutManager
import com.versilistyson.searchflix.presentation.util.showToast
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject


@InternalCoroutinesApi
class DashboardFragment : Fragment(), DataBindingScreen<FragmentDashboardBinding> {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory
    private val viewModel: DashboardViewModel by viewModels {
        daggerViewModelFactory
    }

    private lateinit var categoryAdapter: CategoryAdapter

    private val popularMoviesCategory by lazy {
        Category("Popular Movies", viewModel.popularMoviesState) { viewModel.getPopularMovies() }
    }
    private val topRatedMoviesCategory by lazy {
        Category("Top Rated Movies", viewModel.topRatedMoviesState) { viewModel.getTopRatedMovies() }
    }
    private val upcomingMoviesCategory by lazy {
        Category("Upcoming Movies", viewModel.upcomingMoviesState) { viewModel.getUpcomingMovies() }
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
        setupRecyclerView()
    }



    private fun setupRecyclerView() {

        categoryAdapter = CategoryAdapter(
            viewLifecycleOwner,
            listOf(popularMoviesCategory, topRatedMoviesCategory, upcomingMoviesCategory),
            onCategoryTitleClickListener,
            ::onMediaItemClick
        )

        binding.categoryRecyclerView.layoutManager = provideLinearLayoutManager(LinearLayoutManager.VERTICAL)
        binding.categoryRecyclerView.adapter = categoryAdapter
    }

    // TODO: On click, add a page that goes to a paged fragment that goes to a full paged list screen.
    private val onCategoryTitleClickListener =
        View.OnClickListener {
            Toast.makeText(this.context, "Title Clicked", Toast.LENGTH_SHORT).show()
        }

    private fun onMediaItemClick(media: Media) {

        val toMediaDetailsFragment =
            DashboardFragmentDirections.actionDashboardFragmentToMediaDetailsFragment(media, media.name)

        findNavController().navigate(toMediaDetailsFragment)
    }
}
