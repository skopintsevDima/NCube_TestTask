package com.test.ncubetest.domain.posts.usecase.posts

import com.test.ncubetest.data.posts.repository.model.PostsList
import kotlinx.coroutines.CoroutineScope

interface GetPostsUseCase {
    fun invoke(coroutineScope: CoroutineScope): PostsList
}