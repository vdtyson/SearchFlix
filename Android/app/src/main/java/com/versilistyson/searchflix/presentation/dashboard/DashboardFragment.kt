package com.versilistyson.searchflix.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.databinding.FragmentDashboardBinding
import com.versilistyson.searchflix.di.util.DaggerViewModelFactory
import com.versilistyson.searchflix.di.util.activityInjector
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.presentation.adapters.MediaAdapter
import com.versilistyson.searchflix.presentation.common.activity.DataBindingScreen
import javax.inject.Inject


class DashboardFragment : Fragment(), DataBindingScreen<FragmentDashboardBinding> {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory


    private val viewModel: DashboardViewModel by viewModels {
        daggerViewModelFactory
    }

    private val mediaAdapter by lazy {
        MediaAdapter<Media.Movie>(
            mutableListOf(),
            View.OnClickListener {
                Toast.makeText(this.context, "Item Clicked", Toast.LENGTH_LONG).show()
            }
        )
    }
    private val layoutManager by lazy {
        LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
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
        viewModel.getPopularMovies()
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
                        mediaAdapter.addAll(latestState.popularMovies)
                    }
                }
            }
        )
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mediaAdapter
        binding.recyclerView.layoutManager = layoutManager
    }
}
