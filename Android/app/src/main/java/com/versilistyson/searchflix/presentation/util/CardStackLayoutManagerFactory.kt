package com.versilistyson.searchflix.presentation.util

import android.content.Context
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeableMethod

object CardStackLayoutManagerFactory {
    fun create(
        context: Context?,
        stackFrom: StackFrom? = null,
        visibleCount: Int? = null,
        canScrollVertical: Boolean? = null,
        canScrollHorizontal: Boolean? = null,
        swipeableMethod: SwipeableMethod? = null
    ): CardStackLayoutManager {

        val cardStackLayoutManager = CardStackLayoutManager(context)

        stackFrom?.let {
            cardStackLayoutManager.setStackFrom(stackFrom)
        }

        visibleCount?.let {
            cardStackLayoutManager.setVisibleCount(visibleCount)
        }

        canScrollVertical?.let {
            cardStackLayoutManager.setCanScrollVertical(canScrollVertical)
        }

        canScrollHorizontal?.let {
            cardStackLayoutManager.setCanScrollHorizontal(canScrollHorizontal)
        }

        swipeableMethod?.let {
            cardStackLayoutManager.setSwipeableMethod(swipeableMethod)
        }

        return cardStackLayoutManager
    }
}