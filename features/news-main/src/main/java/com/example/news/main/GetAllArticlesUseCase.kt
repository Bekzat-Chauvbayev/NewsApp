package com.example.news.main

import com.example.news.data.ArticlesRepository
import com.example.news.data.model.Article
import kotlinx.coroutines.flow.Flow

class GetAllArticlesUseCase(private val repository: ArticlesRepository) {
    operator suspend fun invoke() : Flow<List<Article>> {
        return repository.getAll()
    }
}