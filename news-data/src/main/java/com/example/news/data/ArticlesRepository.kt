package com.example.news.data

import com.example.news.data.model.Article
import com.example.news.database.NewsDatabase
import com.example.news.database.models.ArticleDBO
import com.example.newsapi.NewsApi
import com.example.newsapi.models.ArticleDTO
import com.example.newsapi.models.ResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach


class ArticlesRepository(
    private val database: NewsDatabase,
    private val api: NewsApi
) {

    fun getAll(): Flow<RequestResult<List<Article>>>{

        val cachedAllArticles: Flow<RequestResult.Success<List<ArticleDBO>>> = getAllFromDatabase()


        val remoteArticles :Flow<RequestResult<*>> = getAllFromServer()



        cachedAllArticles.map {

        }

       return cachedAllArticles.combine(remoteArticles){

       }
    }

    private fun getAllFromServer(): Flow<RequestResult<*>> {
        return flow {

            emit(api.everything())
        }
            .map { result ->
                if (result.isSuccess) {
                    val responce: ResponseDTO<ArticleDTO> = result.getOrThrow()
                    RequestResult.Success(responce.articles)
                } else {
                    RequestResult.Error(null)
                }
            }
            .onEach { requestResult ->
                if (requestResult is RequestResult.Success) {
                    saveNetResponseToCache(checkNotNull(requestResult.data))
                }
            }



    }

    private suspend fun saveNetResponseToCache(data: List<ArticleDTO>) {
        val dbos = data.map { articleDTO -> articleDTO.toArticlesDbo() }

        database.articlesDao.insert(dbos)
    }

    fun getAllFromDatabase(): Flow<RequestResult.Success<List<ArticleDBO>>> {
        return database.articlesDao.getAll().map {
            RequestResult.Success(it)
        }
    }
    fun search(query: String): Flow<Article>{
        api.everything()
        TODO()
    }
}

sealed class  RequestResult<E>(internal val data: E) {


    class InProgress<E>(data: E) : RequestResult<E>(data)
    class Success<E>(data: E) : RequestResult<E>(data)

    class Error<E>(data: E) : RequestResult<E>(data)

}
    internal fun <T: Any> RequestResult<T?>.requireData(): T{
        return checkNotNull(data)
    }
    internal fun <I, O> RequestResult<I>.map(mapper: (I) -> O): RequestResult<O>{
    val outData = mapper(data)
    return when(this){
        is RequestResult.Success -> RequestResult.Success(outData)
        is RequestResult.Error -> RequestResult.Error(outData)
        is RequestResult.InProgress -> RequestResult.InProgress(outData)
    }
}


