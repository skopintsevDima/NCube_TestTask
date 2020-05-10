package com.test.ncubetest.domain.posts.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.test.ncubetest.domain.posts.model.RedditPost
import com.test.ncubetest.domain.posts.usecase.posts.GetPostsCallback
import kotlinx.coroutines.CoroutineScope

interface PostsRepository {
    fun getAllHot(
        coroutineScope: CoroutineScope,
        getPostsCallback: GetPostsCallback,
        refresh: Boolean
    ): LiveData<PagedList<RedditPost>>
}