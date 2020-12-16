package br.com.alura.technews.repository

import androidx.lifecycle.MutableLiveData
import br.com.alura.technews.model.Resource

abstract class BaseRepository {

    internal fun <T> publicaResouceDeSucesso(liveData: MutableLiveData<Resource<T>>): (data: T?) -> Unit =
        { liveData.postValue(Resource(it)) }

    internal fun publicaResouceVazioSucesso(voidLiveData: MutableLiveData<Resource<Void?>>): () -> Unit {
        return { voidLiveData.postValue(Resource(null)) }
    }

    internal fun publicaResouceVazioDeFalha(voidLiveData: MutableLiveData<Resource<Void?>>): (errorMsg: String?) -> Unit {
        return { voidLiveData.postValue(Resource(null, it)) }
    }

    internal fun <T> publicaResouceDeFalha(liveData: MutableLiveData<Resource<T>>): (errorMsg: String?) -> Unit {
        val resourceAtual = liveData.value
        return { errorMsg ->
            resourceAtual?.let { liveData.postValue(it.criarResouceDeFalha(errorMsg)) }
        }
    }

    internal fun createVoidLivedata(): MutableLiveData<Resource<Void?>> {
        return MutableLiveData()
    }

}
