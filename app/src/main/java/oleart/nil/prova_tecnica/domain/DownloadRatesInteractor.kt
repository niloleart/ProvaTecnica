package oleart.nil.prova_tecnica.domain

import oleart.nil.prova_tecnica.domain.business.rates.Rate
import oleart.nil.prova_tecnica.domain.business.rates.RateDto
import oleart.nil.prova_tecnica.domain.business.rates.RatesRepo
import oleart.nil.prova_tecnica.domain.mapper.RatesMapper
import oleart.nil.prova_tecnica.general.GeneralWSInteractor
import javax.inject.Inject

/**
 * Created by Nil Oleart on 19/01/19.
 */
class DownloadRatesInteractor @Inject constructor(
    var ratesRepo : RatesRepo,
    var ratesMapper : RatesMapper
) : GeneralWSInteractor<MutableList<Rate>, MutableList<RateDto>>(){

    override fun doAction(listener: LoadDataListener<MutableList<Rate>>, listenerError: LoadErrorListener) {
        callWebService(ratesRepo.getRates(), listener, listenerError)
    }

    override fun mapper(data: MutableList<RateDto>): MutableList<Rate> {
        return ratesMapper.reverseMap(data)
    }
}