package com.test.ncubetest.presentation.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.ncubetest.domain.posts.usecase.posts.GetPostsUseCase
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    getPostsUseCase: GetPostsUseCase
): ViewModel() {
    private val postsResult = getPostsUseCase.invoke(viewModelScope)
    val posts = postsResult.pagedList
    val networkState = postsResult.networkState
    val refreshState = postsResult.refreshState

    fun refresh() {
        postsResult.refresh.invoke()
    }

    fun retry(lastItemName: String?) {
        postsResult.retry.invoke(lastItemName)
    }
}