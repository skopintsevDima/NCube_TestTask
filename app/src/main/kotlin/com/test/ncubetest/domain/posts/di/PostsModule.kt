package com.test.ncubetest.domain.posts.di

import com.test.ncubetest.data.posts.api.RedditApi
import com.test.ncubetest.data.posts.dao.PostsDao
import com.test.ncubetest.data.posts.repository.PostsRepositoryImpl
import com.test.ncubetest.di.fragment.FragmentScope
import com.test.ncubetest.domain.posts.repository.PostsRepository
import com.test.ncubetest.domain.posts.usecase.posts.GetPostsUseCase
import com.test.ncubetest.domain.posts.usecase.posts.GetPostsUseCaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class PostsModule {
    @FragmentScope
    @Provides
    @Inject
    fun provideGetPostsUseCase(
        repository: PostsRepository
    ): GetPostsUseCase = GetPostsUseCaseImpl(repository)

    @FragmentScope
    @Provides
    @Inject
    fun providePostsRepository(
        redditApi: RedditApi,
        postsDao: PostsDao
    ): PostsRepository = PostsRepositoryImpl(
        redditApi,
        postsDao
    )
}