package br.com.alura.technews.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.model.Resource
import br.com.alura.technews.repository.NoticiaRepository

class FormularioNoticiaViewModel(private val noticiaRepository: NoticiaRepository) : ViewModel() {

    fun buscaPorId(noticiaId: Long): LiveData<Resource<Noticia?>> {
        return noticiaRepository.buscaPorId(noticiaId)
    }

    fun edita(noticia: Noticia): LiveData<Resource<Noticia?>> = noticiaRepository.edita(noticia)

    fun salva(noticia: Noticia): LiveData<Resource<Noticia?>> = noticiaRepository.salva(noticia)

}
