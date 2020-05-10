package com.test.ncubetest.util.di

import android.content.Context
import android.net.ConnectivityManager
import com.test.ncubetest.util.network.ConnectivityChecker
import com.test.ncubetest.util.network.ConnectivityCheckerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class UtilModule {
    @Provides
    @Singleton
    @Inject
    fun provideConnectivityChecker(context: Context): ConnectivityChecker {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return ConnectivityCheckerImpl(cm)
    }
}