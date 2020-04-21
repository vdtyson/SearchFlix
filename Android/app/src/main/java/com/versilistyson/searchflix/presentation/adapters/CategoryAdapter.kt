package com.versilistyson.searchflix.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.domain.entities.Category

/*
class CategoryAdapter(private val categories: MutableList<Category>, private val mediaLayoutManager: LinearLayoutManager): RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    inner class CategoryHolder(view: View): RecyclerView.ViewHolder(view) {
        private var textViewCategoryTitle = view.findViewById<TextView>(R.id.textViewMediaTitle)
        private var recyclerViewMedia: RecyclerView = view.findViewById(R.id.recyclerViewMedia)
        init {
            recyclerViewMedia.layoutManager = mediaLayoutManager
            recyclerViewMedia.addOnScrollListener(
                object: PaginationListener(mediaLayoutManager) {
                    override fun loadMoreItems() {
                        val adapter = recyclerViewMedia.adapter as MediaPagedAdapter
                        adapter.setLoadingState()
                    }

                    override fun isLastPage(): Boolean = false

                    override fun isLoading(): Boolean {
                        val adapter = recyclerViewMedia.adapter as MediaPagedAdapter
                        return adapter.isLoading()
                    }

                }
            )
        }

        fun bindCategoryTitle(position: Int) {
            textViewCategoryTitle.text = categories[position].title
        }
        fun bindMediaAdapter(position: Int) {
            recyclerViewMedia.adapter = MediaPagedAdapter(categories[position].mediaList.toMutableList())
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryHolder {
        val inflatedLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item_category, parent, false)
        return CategoryHolder(inflatedLayout)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryHolder, position: Int) {
        holder.bindCategoryTitle(position)
        holder.bindMediaAdapter(position)
    }
}*/
