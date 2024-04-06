package com.example.news.main

import androidx.lifecycle.ViewModel
import com.example.news.data.RequestResult
import kotlinx.coroutines.flow.Flow


class NewsMainViewModel(private val getAllArticlesUseCase: GetAllArticlesUseCase) : ViewModel() {

    val state: Flow<RequestResult<List<com.example.news.data.model.Article>>> = getAllArticlesUseCase()



}
private fun RequestResult<List<Article>>.toState(): State {
    return when(this){
        is RequestResult.Error ->State.Error()
        is RequestResult.InProgress -> State.Loading(data)
        is RequestResult.Success ->State.Success(checkNotNull(data))

    }
}

sealed class State{
    object None: State()
    class Loading(val articles: List<Article>?): State()
    class Error: State()
    class Success(val articles: List<Article>): State()
}
