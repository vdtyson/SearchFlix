package com.versilistyson.searchflix.presentation.search

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.presentation.common.UIState

data class MediaSearchState (val liveDataMediaPagedList: PagedList<Media.Movie>?) : UIState