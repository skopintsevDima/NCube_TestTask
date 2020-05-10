package com.test.ncubetest.data.posts.api.di

import android.util.Log
import com.test.ncubetest.data.posts.api.RedditApi
import com.test.ncubetest.data.posts.api.RedditApi.Companion.BASE_URL
import com.test.ncubetest.data.posts.api.RedditApi.Companion.TAG
import com.test.ncubetest.di.fragment.FragmentScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@Module
class NetworkModule {
    @Provides
    @FragmentScope
    @Inject
    fun provideRedditApi(retrofit: Retrofit) = retrofit.create(RedditApi::class.java)

    @Provides
    @FragmentScope
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.d(TAG, message)
                }
            }))
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}