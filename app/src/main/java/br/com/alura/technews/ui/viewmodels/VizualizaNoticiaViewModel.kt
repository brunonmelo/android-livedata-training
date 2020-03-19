package br.com.alura.technews.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.model.Resource
import br.com.alura.technews.repository.NoticiaRepository

class VizualizaNoticiaViewModel(private val repository: NoticiaRepository) : ViewModel() {

    fun buscaPorId(noticiaId: Long): LiveData<Resource<Noticia?>> {
        return repository.buscaPorId(noticiaId)
    }

    fun remove(noticia: Noticia): LiveData<Resource<Void?>> {
        return repository.remove(noticia)
    }

}