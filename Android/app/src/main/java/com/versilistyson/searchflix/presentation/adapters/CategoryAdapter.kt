package com.versilistyson.searchflix.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.domain.entities.Category
import com.versilistyson.searchflix.domain.entities.Media
import kotlinx.coroutines.launch

class CategoryAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val categories: List<Category>,
    private val onCategoryTitleClickListener: View.OnClickListener? = null,
    val onMediaItemClick: ((Media) -> Unit)? = null
): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    inner class CategoryViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val title: TextView = view.findViewById(R.id.categoryTitle)
        private val innerRecyclerView: RecyclerView = view.findViewById(R.id.recyclerViewMedia)

        init {
            innerRecyclerView.layoutManager = provideInnerLayoutManager(view)

            onCategoryTitleClickListener?.let {
                title.setOnClickListener(onCategoryTitleClickListener)
            }
        }

        fun bindTo(category: Category) {

            title.text = category.title

            innerRecyclerView.adapter = MediaAdapter(
                lifecycleOwner,
                category.liveDataMediaList,
                onMediaItemClick
            )

            category.fetchMedia()
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