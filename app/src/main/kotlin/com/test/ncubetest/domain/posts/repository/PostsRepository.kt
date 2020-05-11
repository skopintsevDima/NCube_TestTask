package com.test.ncubetest.domain.posts.repository

import com.test.ncubetest.data.posts.repository.model.PostsList
import kotlinx.coroutines.CoroutineScope

interface PostsRepository {
    fun getAllHot(coroutineScope: CoroutineScope): PostsList
}