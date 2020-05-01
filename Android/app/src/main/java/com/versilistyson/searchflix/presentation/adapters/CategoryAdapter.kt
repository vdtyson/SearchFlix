package com.versilistyson.searchflix.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.domain.entities.Category
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.presentation.adapters.common.BaseRecylerViewAdapter
import com.versilistyson.searchflix.presentation.adapters.common.BaseViewHolder
import com.versilistyson.searchflix.presentation.ui.dashboard.MediaListStateComponent
import kotlinx.coroutines.launch

class CategoryAdapter(
    private val lifecycleOwner: LifecycleOwner,
    categories: List<Category>,
    private val onCategoryTitleClick: ((category: Category) -> Unit)? = null,
    val onMediaItemClick: ((Media) -> Unit)? = null
) : BaseRecylerViewAdapter<Category, CategoryAdapter.CategoryViewHolder>(categories) {


    inner class CategoryViewHolder(view: View) : BaseViewHolder<Category>(view) {

        private val title: TextView = view.findViewById(R.id.categoryTitle)
        private val innerRecyclerView: RecyclerView = view.findViewById(R.id.recyclerViewMedia)
        private val progressBarCategory: ProgressBar = view.findViewById(R.id.progressBarCategory)
        private val tvCategoryContentNotAvailable: TextView =
            view.findViewById(R.id.tvCategoryContentNotAvailable)
        private val mediaAdapter = MediaAdapter(lifecycleOwner, onMediaItemClick = onMediaItemClick)

        init {
            innerRecyclerView.layoutManager = provideInnerLayoutManager(view)
        }

        override fun bindTo(obj: Category) {
            innerRecyclerView.adapter = mediaAdapter
            obj.fetchMedia()
            render(obj)
            title.text = obj.title
            onCategoryTitleClick?.let { onClick ->
                title.setOnClickListener {
                    onClick(obj)
                }
            }
        }

        private fun render(category: Category) {
            category.mediaListState.observe(
                lifecycleOwner,
                Observer {latestState ->
                    lifecycleOwner.lifecycleScope.launch {
                        when (latestState) {

                            is MediaListStateComponent.Loading -> {
                                progressBarCategory.visibility = View.VISIBLE
                                tvCategoryContentNotAvailable.visibility = View.GONE
                                innerRecyclerView.visibility = View.GONE
                            }

                            is MediaListStateComponent.Error -> {
                                //TODO: Handle Errors
                            }

                            is MediaListStateComponent.Data -> {

                                progressBarCategory.visibility = View.GONE

                                when {
                                    latestState.value.isNotEmpty() -> {
                                        tvCategoryContentNotAvailable.visibility = View.GONE
                                        innerRecyclerView.visibility = View.VISIBLE
                                    }

                                    else -> {
                                        tvCategoryContentNotAvailable.visibility = View.VISIBLE
                                        innerRecyclerView.visibility = View.GONE
                                    }
                                }
                                mediaAdapter.postValue(latestState.value)
                            }
                        }
                    }

                }
            )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        val inflatedLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_category, parent, false)

        return CategoryViewHolder(inflatedLayout)
    }

    private fun provideInnerLayoutManager(view: View) =
        LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
}