package com.test.ncubetest.di.fragment

import com.test.ncubetest.data.posts.api.di.NetworkModule
import com.test.ncubetest.domain.posts.di.PostsModule
import com.test.ncubetest.presentation.posts.PostsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [
    FragmentViewModelModule::class,
    NetworkModule::class,
    PostsModule::class
])
interface FragmentComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): FragmentComponent
    }

    fun inject(fragment: PostsFragment)
}