package br.com.alura.technews.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.model.Resource
import br.com.alura.technews.repository.NoticiaRepository

class ListaNoticiasViewModel(private val noticiaRepository: NoticiaRepository) : ViewModel() {

    fun buscaTodos(): LiveData<Resource<List<Noticia>>> {
        return noticiaRepository.buscaTodos()
    }

}