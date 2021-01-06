package br.com.alura.technews.ui.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alura.technews.repository.noticiaRepository.NoticiaRepository

class ListaNoticiaViewModelFactory(private val repository: NoticiaRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(NoticiaRepository::class.java)
            .newInstance(repository)
    }

}