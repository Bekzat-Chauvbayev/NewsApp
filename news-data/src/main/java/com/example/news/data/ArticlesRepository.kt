package com.example.news.data

import com.example.news.data.model.Article
import com.example.news.database.NewsDatabase

import com.example.newsapi.NewsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArticlesRepository(
    private val database: NewsDatabase,
    private val api: NewsApi
) {

    fun getAll(): RequestResult<Flow<List<Article>>>{
        api.everything()
        return RequestResult.InProgress(
            database.articlesDao.getAll().map { articles ->
                articles.map { it.toArticle() }
            }
        )
    }
    fun search(query: String): Flow<Article>{
        api.everything()
        TODO()
    }
}

sealed class  RequestResult<E>(protected val data: E?){


    class InProgress<E>(data: E?): RequestResult<E>(data)
    class  Success<E>(data: E?) : RequestResult<E>(data)

    class  Error<E>(data: E?) : RequestResult<E>(data)
}