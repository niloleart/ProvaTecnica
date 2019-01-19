package oleart.nil.prova_tecnica.general

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

open class GeneralViewModel: ViewModel(){
    val loader: MutableLiveData<Boolean> = MutableLiveData()
    val errorMutable: MutableLiveData<String?> = MutableLiveData()

    fun getErrorListener(): GeneralWSInteractor.LoadErrorListener {
        var errorListener = object : GeneralWSInteractor.LoadErrorListener {
            override fun onError(error: String) {
                errorMutable.postValue(error)
            }
        }
        return errorListener
    }

    fun setError(errorGencat: String){
        errorMutable.postValue(errorGencat)
    }

    fun getErrorCode() : Int? {
        return 0
    }

    override fun onCleared() {
        super.onCleared()
    }

}