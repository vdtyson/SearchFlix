package com.versilistyson.searchflix.presentation.ui.search

import androidx.paging.PagedList
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.presentation.ui.common.UIState

data class MediaSearchState (val liveDataMediaPagedList: PagedList<Media.Movie>?) : UIState