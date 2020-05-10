package com.test.ncubetest.di.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.ncubetest.di.ViewModelFactory
import com.test.ncubetest.di.ViewModelKey
import com.test.ncubetest.presentation.posts.PostsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FragmentViewModelModule {
    @Binds
    @FragmentScope
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @FragmentScope
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    internal abstract fun postsViewModel(viewModel: PostsViewModel): ViewModel
}