package com.test.ncubetest.app.di

import com.test.ncubetest.di.fragment.FragmentComponent
import dagger.Module

@Module(subcomponents = [
    FragmentComponent::class
])
class AppSubcomponentModule