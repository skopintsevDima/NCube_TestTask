package com.test.ncubetest.data.posts.repository.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.test.ncubetest.domain.posts.model.RedditPost

class PostsList (
    val pagedList: LiveData<PagedList<RedditPost>>,
    val networkState: LiveData<NetworkState>,
    val refreshState: LiveData<NetworkState>,
    val refresh: () -> Unit,
    val retry: (lastItemName: String?) -> Unit)