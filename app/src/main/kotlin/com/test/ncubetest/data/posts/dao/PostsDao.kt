package com.test.ncubetest.data.posts.dao

import androidx.paging.DataSource
import androidx.room.*
import com.test.ncubetest.domain.posts.model.RedditPost

@Dao
interface PostsDao {
    @Query("SELECT * FROM posts")
    fun getAllPosts() : DataSource.Factory<Int, RedditPost>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(redditPosts: List<RedditPost>): List<Long>

    @Query("DELETE FROM posts")
    suspend fun deleteAllPosts()
}