package ru.netology.nmedia.ui.uiState

import ru.netology.nmedia.dto.Post

data class PostUiState(
    val posts: List<Post> = emptyList(),
    val empty: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null,
)
