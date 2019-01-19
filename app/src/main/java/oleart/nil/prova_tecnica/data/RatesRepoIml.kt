package oleart.nil.prova_tecnica.data

import oleart.nil.prova_tecnica.data.api.RatesApi
import oleart.nil.prova_tecnica.domain.business.rates.Rate
import oleart.nil.prova_tecnica.domain.business.rates.RateDto
import oleart.nil.prova_tecnica.domain.business.rates.RatesRepo
import retrofit2.Call
import javax.inject.Inject

/**
 * Created by Nil Oleart on 19/01/19.
 */
class RatesRepoIml @Inject constructor(var ratesApi : RatesApi) : RatesRepo{
    override fun getRates(): Call<MutableList<RateDto>> {
        return ratesApi.getRates()
    }

    override fun saveRates(list: MutableList<Rate>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}