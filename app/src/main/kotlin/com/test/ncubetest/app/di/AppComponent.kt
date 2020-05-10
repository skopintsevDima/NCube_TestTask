package com.test.ncubetest.app.di

import android.content.Context
import com.test.ncubetest.data.db.di.DbModule
import com.test.ncubetest.di.fragment.FragmentComponent
import com.test.ncubetest.util.di.UtilModule
import com.test.ncubetest.util.network.ConnectionStateReceiver
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    AppSubcomponentModule::class,
    DbModule::class,
    UtilModule::class
])
interface AppComponent {
    fun applicationContext(): Context

    fun fragmentComponent(): FragmentComponent.Factory

    fun inject(receiver: ConnectionStateReceiver)
}