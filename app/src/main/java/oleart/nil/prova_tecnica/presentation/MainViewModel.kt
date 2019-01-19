package oleart.nil.prova_tecnica.presentation

import android.arch.lifecycle.MutableLiveData
import oleart.nil.prova_tecnica.domain.DownloadRatesInteractor
import oleart.nil.prova_tecnica.domain.business.rates.Rate
import oleart.nil.prova_tecnica.general.GeneralViewModel
import oleart.nil.prova_tecnica.general.GeneralWSInteractor
import javax.inject.Inject

/**
 * Created by Nil Oleart on 19/01/19.
 */
class MainViewModel @Inject constructor(var downloadRatesInteractor : DownloadRatesInteractor
//                                        var saveRatesInteractor : SaveRatesInteractor,
//                                        var downloadTransactionsInteractor : DownloadTransactionsInteractor
//                                        var saveTransactionsInteractor : SaveTransactionsInteractor
) : GeneralViewModel() {

    var ratesList : MutableLiveData<List<Rate>> = MutableLiveData()

    fun getRates() {
        downloadRatesInteractor.doAction(object : GeneralWSInteractor.LoadDataListener<MutableList<Rate>>{
            override fun onLoadData(result: MutableList<Rate>?) {
                if (result != null) {
                    ratesList.postValue(result)

                }
            }
        }, object : GeneralWSInteractor.LoadErrorListener {
            override fun onError(error: String) {

            }
        })
    }
}