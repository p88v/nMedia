package ru.netology.nmedia.ui.uiState

data class NetworkUiState(
    val loading: Boolean = false,
    val error: String? = null,
)
