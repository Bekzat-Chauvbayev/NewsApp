package com.example.newssearchapphometask3

import android.content.Context
import com.example.news.common.AndroidLogcatLogger
import com.example.news.common.AppDispatchers
import com.example.news.common.Logger
import com.example.news.database.NewsDatabase
import com.example.newsapi.NewsApi
import com.example.searchnews.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule{



    @Provides
    @Singleton
    fun provideNewsApi(okHttpClient: OkHttpClient?): NewsApi{

        return NewsApi(
            baseUrl = BuildConfig.NEWS_API_BASE_URL,
            apiKey = BuildConfig.NEWS_API_KEY,
            okHttpClient = okHttpClient

        )
    }



    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase{
        return NewsDatabase(context)
    }

    @Provides
    @Singleton
    fun provideAppCoroutineDispatchers(): AppDispatchers = AppDispatchers()


    @Provides
    fun provideLogger() : Logger = AndroidLogcatLogger()

}