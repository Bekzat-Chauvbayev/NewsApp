package com.example.newsapi

import androidx.annotation.IntRange
import com.example.newsapi.models.Article
import com.example.newsapi.models.Language
import com.example.newsapi.models.Response
import com.example.newsapi.models.SortBy
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

interface NewsApi {
    @GET("/everything")
    fun everything(
        @Query("q") query : String? = null,
        @Query("from") date : Date? =null,
        @Query("to") to : Date? =null,
        @Query("languages") languages : List<Language>? =null,
        @Query("sortBy") sortBy : SortBy? =null,
        @Query("pageSize") @IntRange(from = 0 , to = 100) pageSize : Int =100,
        @Query("page")  @IntRange(from = 1) page : Int =1,

        ):Result<Response<Article>>
}

fun NewsApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json

): NewsApi{
    return retrofit(baseUrl, okHttpClient, json).create()
}

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient? ,
    json: Json
): Retrofit {
    return  Retrofit.Builder()
        .baseUrl(baseUrl)
        .run { if (okHttpClient != null) client(okHttpClient) else this }
        .build()
}


