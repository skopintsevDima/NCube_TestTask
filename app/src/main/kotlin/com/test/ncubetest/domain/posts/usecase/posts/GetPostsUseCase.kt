package com.test.ncubetest.domain.posts.usecase.posts

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.test.ncubetest.domain.posts.model.RedditPost
import kotlinx.coroutines.CoroutineScope

interface GetPostsUseCase {
    fun invoke(
        coroutineScope: CoroutineScope,
        getPostsCallback: GetPostsCallback,
        refresh: Boolean
    ): LiveData<PagedList<RedditPost>>
}