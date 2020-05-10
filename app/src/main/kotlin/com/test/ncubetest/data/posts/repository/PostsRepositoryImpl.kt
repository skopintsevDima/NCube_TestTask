package com.test.ncubetest.data.posts.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.test.ncubetest.data.posts.api.RedditApi
import com.test.ncubetest.data.posts.api.model.ListingResponse
import com.test.ncubetest.data.posts.dao.PostsDao
import com.test.ncubetest.domain.posts.model.RedditPost
import com.test.ncubetest.domain.posts.repository.PostsRepository
import com.test.ncubetest.domain.posts.usecase.posts.GetPostsCallback
import com.test.ncubetest.util.network.ConnectivityChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response

class PostsRepositoryImpl(
    private val redditApi: RedditApi,
    private val postsDao: PostsDao,
    private val connectivityChecker: ConnectivityChecker
): PostsRepository {
    override fun getAllHot(
        coroutineScope: CoroutineScope,
        getPostsCallback: GetPostsCallback,
        refresh: Boolean
    ): LiveData<PagedList<RedditPost>> {
        if (refresh) {
            coroutineScope.launch {
                postsDao.deleteAllPosts()
            }
        }
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setMaxSize(MAX_SIZE)
            .setEnablePlaceholders(false)
            .build()
        return postsDao.getAllPosts().toLiveData(
            config,
            boundaryCallback = PostsBoundaryCallback(SUBREDDIT_ALL, getPostsCallback, coroutineScope)
        )
    }

    private suspend fun insertItemsIntoDb(listingResponse: Response<ListingResponse>) {
        listingResponse.body()?.data?.children?.let { posts ->
            postsDao.insertAll(posts.map { it.data })
        }
    }

    inner class PostsBoundaryCallback(
        private val subreddit: String,
        private val getPostsCallback: GetPostsCallback,
        private val coroutineScope: CoroutineScope
    ): PagedList.BoundaryCallback<RedditPost>() {
        @Volatile
        private var isRequestRunning = false

        override fun onZeroItemsLoaded() {
            coroutineScope.launch {
                if (!isRequestRunning) {
                    isRequestRunning = true
                    try {
                        val response = redditApi.getTop(subreddit, PAGE_SIZE)
                        handleResponse(response)
                    } catch (e: Exception) {
                        handleError(e)
                    }
                }
            }
        }

        override fun onItemAtEndLoaded(itemAtEnd: RedditPost) {
            if (!isRequestRunning) {
                if (connectivityChecker.getConnectionState().value == true){
                    getTopAfter(itemAtEnd)
                } else {
                    getPostsCallback.setupPendingLoading { getTopAfter(itemAtEnd) }
                }
            }
        }

        private fun getTopAfter(lastItemLoaded: RedditPost) {
            coroutineScope.launch {
                isRequestRunning = true
                try {
                    val response = redditApi.getTopAfter(subreddit, lastItemLoaded.name, PAGE_SIZE)
                    handleResponse(response)
                } catch (e: Exception) {
                    handleError(e)
                }
            }
        }

        private suspend fun handleResponse(response: Response<ListingResponse>) {
            isRequestRunning = false
            if (response.isSuccessful) {
                insertItemsIntoDb(response)
            } else {
                var t = Throwable()
                response.errorBody()?.string()?.let { t = Throwable(it) }
                handleError(t)
            }
        }

        private fun handleError(t: Throwable) {
            Log.d(TAG, "Loading posts failed: ${t.message}")
            getPostsCallback.onError(t)
        }
    }

    companion object {
        private const val TAG = "PostsRepositoryImpl"
        private const val PAGE_SIZE = 10
        private const val MAX_SIZE = 100
        private const val SUBREDDIT_ALL = "all"
    }
}