package com.versilistyson.searchflix.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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
    private val linearLayoutManager by lazy {
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private val popularMovieCategory by lazy {
        Category(
            "Popular Movies",
            MutableLiveData()
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
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        renderState()
    }

    private fun renderState() {
        viewModel.dashboardState.observe(
            viewLifecycleOwner,
            Observer { latestState ->
                when (latestState) {
                    is DashboardState.Failed -> {
                        Toast.makeText(this.context, "Failure", Toast.LENGTH_LONG).show()
                    }
                    is DashboardState.Loaded -> {
                        popularMovieCategory.updateMediaList(latestState.popularMovies)
                    }
                    is DashboardState.LoadingCategory -> {
                        Toast.makeText(this.context, "Loading Category", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
    }

    private fun setupRecyclerView() {

        popularMovieCategory.fetcherFn = { viewModel.getPopularMovies() }

        categoryAdapter = CategoryAdapter(
            viewLifecycleOwner,
            listOf(popularMovieCategory),
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
        Toast.makeText(this.context, "Media Title: ${media.name}", Toast.LENGTH_LONG).show()
    }
}
