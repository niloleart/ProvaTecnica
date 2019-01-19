package oleart.nil.prova_tecnica.general

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.*
import retrofit2.Call
import java.io.IOException


abstract class GeneralWSInteractor<Model, DtoObject> {
    var list: List<String>? = null

    interface LoadDataListener<in Model> {
        fun onLoadData(result: Model?)
    }

    interface LoadErrorListener {
        fun onError(error: String)
    }

    fun callWebService(call: Call<DtoObject>, listener: LoadDataListener<Model>?, listenerError: LoadErrorListener?) {
        GlobalScope.launch(Dispatchers.Main) {
            async(Dispatchers.IO) {
                try {
                    val result = call.execute()
                    if (result.isSuccessful) {
                        var resultData = result.body()
                        if (resultData != null) {
                            listener?.onLoadData(mapper(resultData))
                        } else {
                            listenerError?.onError("")
                        }
                    } else {
                        if (result.errorBody() != null)
                            listenerError?.onError("")
                        else
                            listenerError?.onError("")
                    }
                } catch (e: IOException) {
                    listenerError?.onError("")
                }
            }
        }
    }

    /**
     * Template to execute default action
     */
    abstract fun doAction(listener: LoadDataListener<Model>, listenerError: LoadErrorListener)

    /**
     * Template to mapper objects
     */
    abstract fun mapper(data: DtoObject): Model

    /**
     * Template to set params
     */
    open fun setParams(list: List<String>?) {
        this.list = list
    }

    /**
     * Template to get Params
     */
    protected open fun getParams(): List<String>? {
        return list
    }
}