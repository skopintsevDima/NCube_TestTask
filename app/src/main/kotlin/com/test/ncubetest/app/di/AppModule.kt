package com.test.ncubetest.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class AppModule(
    private val context: Context
) {
    @Singleton
    @Provides
    fun provideContext(): Context = context

    @Singleton
    @Provides
    fun provideIoExecutor(): Executor = IO_EXECUTOR

    companion object {
        private val IO_EXECUTOR = Executors.newCachedThreadPool()
    }
}