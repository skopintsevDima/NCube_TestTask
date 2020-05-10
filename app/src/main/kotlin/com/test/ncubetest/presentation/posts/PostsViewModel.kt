package com.test.ncubetest.presentation.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.test.ncubetest.domain.posts.model.RedditPost
import com.test.ncubetest.domain.posts.usecase.posts.GetPostsUseCase
import com.test.ncubetest.domain.posts.usecase.posts.GetPostsCallback
import com.test.ncubetest.util.network.ConnectivityChecker
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val connectivityChecker: ConnectivityChecker
): ViewModel() {
    fun loadPosts(
        getPostsCallback: GetPostsCallback,
        refresh: Boolean
    ): LiveData<PagedList<RedditPost>> = getPostsUseCase.invoke(
        viewModelScope,
        getPostsCallback,
        refresh
    )

    fun getNetworkState(): LiveData<Boolean> = connectivityChecker.getConnectionState()
}