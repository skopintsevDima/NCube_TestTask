package com.test.ncubetest.data.posts.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.test.ncubetest.data.posts.api.RedditApi
import com.test.ncubetest.data.posts.api.model.ListingResponse
import com.test.ncubetest.data.posts.dao.PostsDao
import com.test.ncubetest.data.posts.repository.model.NetworkState
import com.test.ncubetest.data.posts.repository.model.PostsList
import com.test.ncubetest.domain.posts.model.RedditPost
import com.test.ncubetest.domain.posts.repository.PostsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response

class PostsRepositoryImpl(
    private val redditApi: RedditApi,
    private val postsDao: PostsDao
): PostsRepository {
    override fun getAllHot(coroutineScope: CoroutineScope): PostsList {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setMaxSize(MAX_SIZE)
            .setEnablePlaceholders(false)
            .build()
        val boundaryCallback = PostsBoundaryCallback(SUBREDDIT_ALL, coroutineScope)
        val postsLivePagedList = postsDao.getAllPosts().toLiveData(
            config,
            boundaryCallback = boundaryCallback
        )

        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = refreshTrigger.switchMap {
            boundaryCallback.refresh()
        }

        return PostsList(
            postsLivePagedList,
            boundaryCallback.networkState,
            refreshState,
            { refreshTrigger.value = null },
            { boundaryCallback.retry(it) }
        )
    }

    private suspend fun insertItemsIntoDb(listingResponse: Response<ListingResponse>) {
        listingResponse.body()?.data?.children?.let { posts ->
            postsDao.insertAll(posts.map { it.data })
        }
    }

    inner class PostsBoundaryCallback(
        private val subreddit: String,
        private val coroutineScope: CoroutineScope
    ): PagedList.BoundaryCallback<RedditPost>() {
        val networkState = MutableLiveData<NetworkState>()

        fun refresh(): LiveData<NetworkState> {
            val refreshState = MutableLiveData<NetworkState>()
            coroutineScope.launch {
                val errorHandler = { msg: String? ->
                    Log.d(TAG, "Refreshing failed: $msg")
                    refreshState.postValue(NetworkState.error(msg))
                }
                try {
                    refreshState.postValue(NetworkState.LOADING)
                    val response = redditApi.getTop(subreddit, PAGE_SIZE)
                    if (response.isSuccessful) {
                        refreshState.postValue(NetworkState.LOADED)
                        postsDao.deleteAllPosts()
                        insertItemsIntoDb(response)
                    } else {
                        var t = Throwable()
                        response.errorBody()?.string()?.let { t = Throwable(it) }
                        errorHandler.invoke(t.message)
                    }
                } catch (e: Exception) {
                    errorHandler.invoke(e.message)
                }
            }
            return refreshState
        }

        fun retry(lastItemName: String?): LiveData<NetworkState> {
            if (lastItemName == null) getTop()
            else getTopAfter(lastItemName)
            return networkState
        }

        override fun onZeroItemsLoaded() {
            getTop()
        }

        private fun getTop() {
            if (networkState.value == null || networkState.value != NetworkState.LOADING) {
                coroutineScope.launch {
                    networkState.postValue(NetworkState.LOADING)
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
            getTopAfter(itemAtEnd.name)
        }

        private fun getTopAfter(lastItemLoadedName: String) {
            if (networkState.value == null || networkState.value != NetworkState.LOADING) {
                coroutineScope.launch {
                    networkState.postValue(NetworkState.LOADING)
                    try {
                        val response = redditApi.getTopAfter(subreddit, lastItemLoadedName, PAGE_SIZE)
                        handleResponse(response)
                    } catch (e: Exception) {
                        handleError(e)
                    }
                }
            }
        }

        private suspend fun handleResponse(response: Response<ListingResponse>) {
            if (response.isSuccessful) {
                networkState.postValue(NetworkState.LOADED)
                insertItemsIntoDb(response)
            } else {
                var t = Throwable()
                response.errorBody()?.string()?.let { t = Throwable(it) }
                handleError(t)
            }
        }

        private fun handleError(t: Throwable) {
            Log.d(TAG, "Loading posts failed: ${t.message}")
            networkState.postValue(NetworkState.error(t.message))
        }
    }

    companion object {
        private const val TAG = "PostsRepositoryImpl"
        private const val PAGE_SIZE = 10
        private const val MAX_SIZE = 100
        private const val SUBREDDIT_ALL = "all"
    }
}