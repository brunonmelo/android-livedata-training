package br.com.alura.technews.repository

import androidx.lifecycle.MutableLiveData
import br.com.alura.technews.model.Resource

abstract class BaseRepository {

    internal fun publicaResouceVazioSucesso(voidLiveData: MutableLiveData<Resource<Void?>>): () -> Unit {
        return { voidLiveData.value = Resource() }
    }

    fun  MutableLiveData<Resource<Void?>>.publicaResouceVazioSucesso() {
        this.postValue(Resource())
    }

    internal fun publicaResouceVazioDeFalha(voidLiveData: MutableLiveData<Resource<Void?>>): (errorMsg: String?) -> Unit {
        return { voidLiveData.postValue(Resource(null, it)) }
    }

    internal fun createVoidLivedata(): MutableLiveData<Resource<Void?>> {
        return MutableLiveData()
    }

}
