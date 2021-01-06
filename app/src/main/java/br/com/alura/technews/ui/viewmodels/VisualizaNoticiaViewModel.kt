package br.com.alura.technews.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.model.Resource
import br.com.alura.technews.repository.noticiaRepository.NoticiaRepository

class VisualizaNoticiaViewModel(
    private val repository: NoticiaRepository
) : ViewModel() {

    fun buscaPorId(noticiaId: Long): LiveData<Resource<Noticia?>> = repository.buscaPorId(noticiaId)

    fun remove(noticiaId: Long): LiveData<Resource<Void?>> = repository.remove(noticiaId)

}