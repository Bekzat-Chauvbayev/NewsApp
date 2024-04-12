package com.example.news.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.RequestResult
import com.example.news.data.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
internal class NewsMainViewModel @Inject constructor(
     getAllArticlesUseCase: Provider<GetAllArticlesUseCase>,
) : ViewModel() {

    val state: StateFlow<State> = getAllArticlesUseCase.get().invoke().map { it.toState() }.stateIn(viewModelScope, SharingStarted.Lazily, State.None) as StateFlow<State>


    fun forceUpdate(){

    }
}



internal fun  RequestResult<List<ArticleUI>>.toState():State {
    return when(this){
        is RequestResult.Error ->State.Error(data)
        is RequestResult.InProgress -> State.Loading(data)
        is RequestResult.Success ->State.Success(data)

    }
}






internal sealed class State{
    object None: State()
    class Loading(val articles: List<ArticleUI>? = null): State()
    class Error(val articles: List<ArticleUI>? = null): State()
    class Success(val articles: List<ArticleUI>): State()
}
