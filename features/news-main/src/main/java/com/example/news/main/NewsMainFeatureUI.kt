package com.example.news.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NewsMain(){
    NewsMain(viewModel = viewModel())
}


@Composable
internal fun NewsMain(viewModel: NewsMainViewModel = viewModel()){
    val state by viewModel.state.collectAsState()
    when(val currentState = state){
        is State.Success -> Articles(currentState)
        is State.Error -> TODO()
        is State.Loading -> TODO()
        State.None -> TODO()
    }

}
@Composable
private fun Articles(state: State.Success) {
    LazyColumn{
        items(state.articles){ article ->
        key(article.id) {
            Article(article)
        }
        }
    }
}
@Preview
@Composable
internal fun Article(@PreviewParameter(ArticlePreviewProvider::class) article: ArticleUI) {
    Column{
        Text(text = article.title, style = MaterialTheme.typography.headlineMedium, maxLines = 1)
        Text(text = article.description, style = MaterialTheme.typography.bodyMedium, maxLines = 3)
    }
}

private class ArticlePreviewProvider : PreviewParameterProvider<ArticleUI>{
    override val values = sequenceOf(ArticleUI(1 , "dsadas", "fdsfdsfs" , "dsfsdf" , "fdsfs"),
        ArticleUI(2 , "dsadas", "fdsfdsfs" , "dsfsdf" , "fdsfs"),
        ArticleUI(3 , "dsadas", "fdsfdsfs" , "dsfsdf" , "fdsfs"))

}
