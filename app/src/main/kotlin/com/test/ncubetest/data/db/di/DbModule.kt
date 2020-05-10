package com.test.ncubetest.data.db.di

import android.content.Context
import androidx.room.Room
import com.test.ncubetest.data.db.RedditDatabase
import com.test.ncubetest.data.db.RedditDatabase.Companion.APP_DATABASE_NAME
import com.test.ncubetest.data.posts.dao.PostsDao
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class DbModule {
    @Provides
    @Singleton
    @Inject
    fun providePostsDao(redditDatabase: RedditDatabase): PostsDao = redditDatabase.postsDao()

    @Provides
    @Singleton
    @Inject
    fun provideAppDatabase(applicationContext: Context): RedditDatabase = Room.databaseBuilder(
        applicationContext,
        RedditDatabase::class.java, APP_DATABASE_NAME
    ).build()
}