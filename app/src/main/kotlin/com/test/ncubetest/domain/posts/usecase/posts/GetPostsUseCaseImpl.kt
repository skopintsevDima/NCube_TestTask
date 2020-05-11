package com.test.ncubetest.domain.posts.usecase.posts

import com.test.ncubetest.data.posts.repository.model.PostsList
import com.test.ncubetest.domain.posts.repository.PostsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.plus
import javax.inject.Inject

class GetPostsUseCaseImpl @Inject constructor(
    private val postsRepository: PostsRepository
) : GetPostsUseCase {
    override fun invoke(coroutineScope: CoroutineScope): PostsList
            = postsRepository.getAllHot(coroutineScope + Dispatchers.IO)
}