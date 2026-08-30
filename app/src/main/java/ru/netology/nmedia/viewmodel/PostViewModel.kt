package ru.netology.nmedia.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.netology.nmedia.api.ApiProvider
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.ui.uiState.NetworkUiState
import ru.netology.nmedia.ui.uiState.PostUiState
import kotlinx.coroutines.flow.combine

private val empty = Post()

class PostViewModel(application: Application) : AndroidViewModel(application) {


    private val dao = AppDb.getInstance(application).postDao()
    private val repository: PostRepository = PostRepositoryImpl(
        dao = dao,
        apiService = ApiProvider.apiService,
    )

    init {
        loadPostsFromServer()
    }

    val networkUiState = MutableStateFlow(NetworkUiState())

    val uiStaet = combine(
        repository.getAll(),
        networkUiState
    ) { posts, network ->
        PostUiState(
            posts = posts,
            empty = posts.isEmpty(),
            loading = network.loading,
            error = network.error,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(10_000),
        initialValue = PostUiState(loading = true)
    )


    val edited = MutableLiveData(empty)
    fun like(id: Long) {
        viewModelScope.launch {
            repository.like(id)
        }
    }

    fun share(id: Long) {
        viewModelScope.launch {
            repository.share(id)
        }
    }


    fun edit() {
        edited.value = Post()
    }


    fun save(content: String) {
        viewModelScope.launch {

            try{
                edited.value?.let {
                    val trimmed = content.trim()

                    if (it.content != trimmed) {
                        Log.d("SAVE", "Перед отправкой: $it")

                        repository.save(it.copy(content = trimmed))

                        Log.d("SAVE", "Успешно отправили")
                    }
                }
                edited.value = empty
            } catch(e: Exception){
                Log.e("SAVE", "Ошибка при сохранении", e)
            }


        }
    }

    fun remove(id: Long) {
        viewModelScope.launch {
            repository.remove(id)
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun loadPostsFromServer() {
        viewModelScope.launch {
            networkUiState.value = networkUiState.value.copy(
                loading = true
            )
            try {
                Log.d("RETROFIT", "Start")
                repository.loadFromServer()
                networkUiState.value = networkUiState.value.copy(
                    loading = false,
                    error = null
                )
                Log.d("RETROFIT", "Запустилось")
            } catch (
                e: Exception
            ) {
                Log.d("RETROFIT", "exeption", e)
                networkUiState.value = networkUiState.value.copy(
                    loading = false,
                    error = e.message
                )
            }

        }
    }

}