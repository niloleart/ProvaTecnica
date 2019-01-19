package oleart.nil.prova_tecnica.domain.business.rates

import retrofit2.Call

/**
 * Created by Nil Oleart on 19/01/19.
 */
interface RatesRepo {
    fun getRates() : Call<MutableList<RateDto>>

    fun saveRates(list : MutableList<Rate>)
}