package br.com.alura.technews.repository.noticiaRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.technews.asynctask.BaseAsyncTask
import br.com.alura.technews.database.dao.NoticiaDAO
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.model.Resource
import br.com.alura.technews.repository.BaseRepository
import br.com.alura.technews.retrofit.webclient.NoticiaWebClient

class NoticiaRepositoryImpl(
    private val dao: NoticiaDAO,
    private val webclient: NoticiaWebClient
) : BaseRepository(), NoticiaRepository {

    private val mediator: MediatorLiveData<Resource<List<Noticia>>> = MediatorLiveData()

    override fun buscaTodos(): LiveData<Resource<List<Noticia>>> {
        val falhasWepApi: MutableLiveData<Resource<List<Noticia>>> = MutableLiveData(
            Resource(listOf())
        )

        mediator.addSource(buscaInterno()) { mediator.postValue(Resource(it)) }
        mediator.addSource(falhasWepApi) {
            mediator.postValue(it)
        }

        buscaNaApi(
            quandoFalha = { mediator.postValue(Resource(error = it)) }
        )
        return mediator
    }

    override fun salva(noticia: Noticia): LiveData<Resource<Void?>> {
        val voidLiveData = createVoidLivedata()
        salvaNaApi(
            noticia,
            quandoSucesso = publicaResouceVazioSucesso(voidLiveData),
            quandoFalha = publicaResouceVazioDeFalha(voidLiveData)
        )
        return voidLiveData
    }

    override fun remove(noticia: Noticia): MutableLiveData<Resource<Void?>> {
        val voidLiveData: MutableLiveData<Resource<Void?>> = createVoidLivedata()
        removeNaApi(
            noticia,
            quandoSucesso = publicaResouceVazioSucesso(voidLiveData),
            quandoFalha = publicaResouceVazioDeFalha(voidLiveData)
        )
        return voidLiveData
    }

    override fun edita(noticia: Noticia): LiveData<Resource<Void?>> {
        val voidLiveData = createVoidLivedata()
        editaNaApi(
            noticia,
            quandoSucesso = publicaResouceVazioSucesso(voidLiveData),
            quandoFalha = publicaResouceVazioDeFalha(voidLiveData)
        )
        return voidLiveData
    }

    override fun buscaPorId(noticiaId: Long): LiveData<Noticia?> {
        return dao.buscaPorId(noticiaId)
    }

    private fun buscaNaApi(quandoFalha: (erro: String?) -> Unit) {
        webclient.buscaTodas(
            quandoSucesso = { noticiasNovas ->
                noticiasNovas?.let {
                    salvaInterno(noticiasNovas)
                }
            }, quandoFalha = quandoFalha
        )
    }

    private fun buscaInterno(): LiveData<List<Noticia>> = dao.buscaTodos()

    private fun salvaNaApi(
        noticia: Noticia,
        quandoSucesso: () -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.salva(
            noticia,
            quandoSucesso = {
                it?.let { noticiaSalva ->
                    salvaInterno(noticiaSalva, quandoSucesso)
                }
            }, quandoFalha = quandoFalha
        )
    }

    private fun salvaInterno(
        noticias: List<Noticia>
    ) {
        BaseAsyncTask(
            quandoExecuta = {
                dao.salva(noticias)
            }
        ).execute()
    }

    private fun salvaInterno(
        noticia: Noticia,
        quandoSucesso: () -> Unit
    ) {
        BaseAsyncTask(quandoExecuta = {
            dao.salva(noticia)
        }, quandoFinaliza = {
            quandoSucesso()
        }).execute()

    }

    private fun removeNaApi(
        noticia: Noticia,
        quandoSucesso: () -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.remove(
            noticia.id,
            quandoSucesso = {
                removeInterno(noticia, quandoSucesso)
            },
            quandoFalha = quandoFalha
        )
    }


    private fun removeInterno(
        noticia: Noticia,
        quandoSucesso: () -> Unit
    ) {
        BaseAsyncTask(quandoExecuta = {
            dao.remove(noticia)
        }, quandoFinaliza = {
            quandoSucesso()
        }).execute()
    }

    private fun editaNaApi(
        noticia: Noticia,
        quandoSucesso: () -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.edita(
            noticia.id, noticia,
            quandoSucesso = { noticiaEditada ->
                noticiaEditada?.let {
                    salvaInterno(noticiaEditada, quandoSucesso)
                }
            }, quandoFalha = quandoFalha
        )
    }

}
