package com.versilistyson.searchflix.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.domain.entities.Category
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.presentation.dashboard.MediaListStateComponent

class CategoryAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val categories: List<Category>,
    private val onCategoryTitleClickListener: View.OnClickListener? = null,
    val onMediaItemClick: ((Media) -> Unit)? = null
): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    inner class CategoryViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        private val title: TextView = view.findViewById(R.id.categoryTitle)
        private val innerRecyclerView: RecyclerView = view.findViewById(R.id.recyclerViewMedia)
        private val progressBarCategory: ProgressBar = view.findViewById(R.id.progressBarCategory)
        private val tvCategoryContentNotAvailable: TextView = view.findViewById(R.id.tvCategoryContentNotAvailable)
        private val mediaAdapter = MediaAdapter(lifecycleOwner, onMediaItemClick)

        init {
            innerRecyclerView.layoutManager = provideInnerLayoutManager(view)

            onCategoryTitleClickListener?.let {
                title.setOnClickListener(onCategoryTitleClickListener)
            }
        }

        fun bindTo(category: Category) {

            render(category)

            innerRecyclerView.adapter = mediaAdapter

            title.text = category.title


            category.fetchMedia()
        }

        private fun render(category: Category) {
            category.mediaListState.observe(
                lifecycleOwner,
                Observer {latestState ->
                    when(latestState) {

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

                            mediaAdapter.liveDataMediaList.postValue(latestState.value)
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

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {

        val category = categories[position]
        holder.bindTo(category)
    }

    private fun provideInnerLayoutManager(view: View) =
        LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
}