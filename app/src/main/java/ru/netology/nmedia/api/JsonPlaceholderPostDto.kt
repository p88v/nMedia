package ru.netology.nmedia.api

data class JsonPlaceholderPostDto(
    val userId: Long,
    val id: Long,
    val title: String,
    val body: String,
)