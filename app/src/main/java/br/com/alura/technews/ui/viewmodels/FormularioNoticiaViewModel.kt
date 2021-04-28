package br.com.alura.technews.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.model.Resource
import br.com.alura.technews.repository.noticiaRepository.NoticiaRepository

class FormularioNoticiaViewModel(private val noticiaRepository: NoticiaRepository) : ViewModel() {

    fun buscaPorId(noticiaId: Long): LiveData<Noticia?> =
        noticiaRepository.buscaPorId(noticiaId)

    fun salva(noticia: Noticia): LiveData<Resource<Void?>> {
        return if (noticia.id > 0) {
            noticiaRepository.edita(noticia)
        } else {
            noticiaRepository.salva(noticia)
        }
    }

}
