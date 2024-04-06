package com.example.news.data

import com.example.news.data.model.Article
import com.example.news.database.NewsDatabase
import com.example.news.database.models.ArticleDBO
import com.example.newsapi.NewsApi
import com.example.newsapi.models.ArticleDTO
import com.example.newsapi.models.ResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach


class ArticlesRepository(
    private val database: NewsDatabase,
    private val api: NewsApi,

) {

    fun getAll(
        mergeStrategy: MergeStrategy<RequestResult<List<Article>>> = DefaultRequestResponseMergeStrategy()
    ): Flow<RequestResult<List<Article>>>{

        val cachedAllArticles = getAllFromDatabase()
            .map { result ->
                result.map { articlesDbos ->
                    articlesDbos.map { it.toArticle()
                    }
                }
            }
        val remoteArticles = getAllFromServer() .map { result: RequestResult<ResponseDTO<ArticleDTO>> ->
            result.map { responce ->
                responce.articles.map { it.toArticle()
                }
            }
        }

       return cachedAllArticles.combine(remoteArticles, mergeStrategy::merge)
           .flatMapLatest { result ->
               if (result is RequestResult.Success){
                   database.articlesDao.observeAll()
                       .map { dbos-> dbos.map { it.toArticle() } }
                       .map { RequestResult.Success(it) }
               }else{
                   flowOf(result)
               }

           }
        }



    private fun getAllFromServer(): Flow<RequestResult<ResponseDTO<ArticleDTO>>> {

       val apiRequest = flow { emit((api.everything())) }.onEach { result ->
            if(result.isSuccess){
                saveNetResponseToCache(checkNotNull(result.getOrThrow().articles))
            }

        }
           .map { it.toRequestResult() }
       val start = flowOf<RequestResult<ResponseDTO<ArticleDTO>>>(RequestResult.InProgress())
      return  merge(apiRequest,start)


    }

    private fun getAllFromDatabase(): Flow<RequestResult<List<ArticleDBO>>> {

        val dbRequest = database.articlesDao::getAll.asFlow()
         .map { RequestResult.Success(it) }
        val start = flowOf<RequestResult<List<ArticleDBO>>>(RequestResult.InProgress())

        return merge(start,dbRequest)
    }

    private suspend fun saveNetResponseToCache(data: List<ArticleDTO>) {
        val dbos = data.map { articleDTO -> articleDTO.toArticlesDbo() }

        database.articlesDao.insert(dbos)
    }
    fun search(query: String): Flow<Article>{
        api.everything()
        TODO()
    }
}



