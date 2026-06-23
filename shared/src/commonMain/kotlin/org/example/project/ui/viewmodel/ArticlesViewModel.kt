package org.example.project.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.example.project.data.model.Article
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.project.data.repository.ArticlesRepository

sealed class UIState{
    data object Loading: UIState()
    data class Success(val articles: List<Article>): UIState()
    data class Error(val message: String): UIState()
}

class ArticlesViewModel (private val repository: ArticlesRepository = ArticlesRepository()) : ViewModel() {
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    init {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch() {
            try {
                val articles = repository.getArticles()
                _uiState.value = UIState.Success(articles)
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Unknown error")
            }
        }
    }
}