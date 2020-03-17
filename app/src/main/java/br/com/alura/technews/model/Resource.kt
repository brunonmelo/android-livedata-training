package br.com.alura.technews.model

data class Resource<T>(
    val dado: T?,
    val error: String? = null
) {

    fun criarResouceDeFalha(errorMsg: String?): Resource<T> {
        return Resource(dado, errorMsg)
    }
}