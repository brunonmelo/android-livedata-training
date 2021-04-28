package br.com.alura.technews.model

data class Resource<T>(
    val dado: T? = null,
    val error: String? = null
)