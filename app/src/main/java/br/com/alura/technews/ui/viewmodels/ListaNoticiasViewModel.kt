package br.com.alura.technews.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository

class ListaNoticiasViewModel(private val noticiaRepository: NoticiaRepository) : ViewModel() {

    fun buscaTodos(
        quandoSucesso: (List<Noticia>) -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) = noticiaRepository.buscaTodos(quandoSucesso, quandoFalha)

}