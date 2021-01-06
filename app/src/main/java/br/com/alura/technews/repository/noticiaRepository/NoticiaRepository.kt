package br.com.alura.technews.repository.noticiaRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.model.Resource

interface NoticiaRepository {

    fun buscaTodos(): LiveData<Resource<List<Noticia>>>
    fun salva(noticia: Noticia): LiveData<Resource<Void?>>
    fun remove(noticiaId: Long): MutableLiveData<Resource<Void?>>
    fun edita(noticia: Noticia): LiveData<Resource<Void?>>
    fun buscaPorId(noticiaId: Long): LiveData<Resource<Noticia?>>

}