package br.com.alura.technews.model

data class Resource<T>(
    val dado: T?,
    val error: String? = null
)