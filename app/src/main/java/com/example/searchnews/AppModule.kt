package com.example.searchnews

import android.content.Context
import com.example.news.common.AppDispatchers
import com.example.news.database.NewsDatabase
import com.example.newsapi.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import logcat.AndroidLogcatLogger

import java.util.logging.Logger
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient?{
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }

        return null

    }

    @Provides
    @Singleton
    fun provideNewsApi(okHttpClient: OkHttpClient?): NewsApi {
        return NewsApi(
            baseUrl = BuildConfig.NEWS_API_BASE_URL,
            apiKey = BuildConfig.NEWS_API_KEY,
            okHttpClient = okHttpClient,
        )
    }

    object AppModule {
        @Provides
        @Singleton
        fun provideAppCoroutineDispatchers(): AppDispatchers  = AppDispatchers()

        @Provides
        fun provideLogger(): AndroidLogcatLogger.Companion = AndroidLogcatLogger

    }