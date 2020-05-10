package com.test.ncubetest.domain.posts.usecase.posts

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.test.ncubetest.domain.posts.model.RedditPost
import com.test.ncubetest.domain.posts.repository.PostsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.plus
import javax.inject.Inject

class GetPostsUseCaseImpl @Inject constructor(
    private val postsRepository: PostsRepository
) : GetPostsUseCase {
    override fun invoke(
        coroutineScope: CoroutineScope,
        getPostsCallback: GetPostsCallback,
        refresh: Boolean
    ): LiveData<PagedList<RedditPost>> = postsRepository.getAllHot(
        coroutineScope + Dispatchers.IO,
        getPostsCallback,
        refresh
    )
}