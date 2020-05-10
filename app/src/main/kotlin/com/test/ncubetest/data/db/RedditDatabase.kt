package com.test.ncubetest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.ncubetest.data.db.RedditDatabase.Companion.APP_DATABASE_VERSION
import com.test.ncubetest.data.posts.dao.PostsDao
import com.test.ncubetest.domain.posts.model.RedditPost

@Database(entities = [RedditPost::class], version = APP_DATABASE_VERSION)
abstract class RedditDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao

    companion object {
        const val APP_DATABASE_VERSION = 1
        const val APP_DATABASE_NAME = "Test.db"
    }
}