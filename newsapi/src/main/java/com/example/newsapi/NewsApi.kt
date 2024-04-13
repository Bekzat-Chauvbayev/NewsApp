package com.example.newsapi

import androidx.annotation.IntRange
import com.example.newsapi.models.ArticleDTO
import com.example.newsapi.models.Language
import com.example.newsapi.models.ResponseDTO
import com.example.newsapi.models.SortBy
import com.example.newsapi.utils.NewsApiKeyInterceptor
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
        @Query("languages") languages : List<@JvmSuppressWildcards Language>? =null,
        @Query("sortBy") sortBy : SortBy? =null,
        @Query("pageSize") @IntRange(from = 0 , to = 100) pageSize : Int =100,
        @Query("page")  @IntRange(from = 1) page : Int =1,

        ):Result<ResponseDTO<ArticleDTO>>
}

fun NewsApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json,
    apiKey: String

): NewsApi{
    return retrofit(baseUrl,apiKey, okHttpClient, json).create()
}

private fun retrofit(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient? ,
    json: Json
): Retrofit {

   val modifiedOkHttpClient = (okHttpClient?.newBuilder()?: OkHttpClient.Builder()).addInterceptor(NewsApiKeyInterceptor(apiKey)).build()
    return  Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(modifiedOkHttpClient)
        .build()
}


