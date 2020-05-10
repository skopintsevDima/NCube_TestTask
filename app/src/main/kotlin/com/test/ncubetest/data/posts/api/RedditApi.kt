package com.test.ncubetest.data.posts.api

import com.test.ncubetest.data.posts.api.model.ListingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditApi {
    @GET("/r/{$PATH_SUBREDDIT}/hot.json")
    suspend fun getTop(
        @Path(PATH_SUBREDDIT) subreddit: String,
        @Query(QUERY_LIMIT) limit: Int): Response<ListingResponse>

    @GET("/r/{$PATH_SUBREDDIT}/hot.json")
    suspend fun getTopAfter(
        @Path(PATH_SUBREDDIT) subreddit: String,
        @Query(QUERY_AFTER) after: String,
        @Query(QUERY_LIMIT) limit: Int): Response<ListingResponse>

    companion object {
        const val TAG = "RedditApi"
        const val BASE_URL = "https://www.reddit.com"
        private const val PATH_SUBREDDIT = "subreddit"
        private const val QUERY_AFTER = "after"
        private const val QUERY_LIMIT = "limit"
    }
}