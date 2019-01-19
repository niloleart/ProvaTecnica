package oleart.nil.prova_tecnica.domain.mapper

import oleart.nil.prova_tecnica.domain.business.rates.Rate
import oleart.nil.prova_tecnica.domain.business.rates.RateDto
import oleart.nil.prova_tecnica.general.GeneralMapper
import javax.inject.Inject

/**
 * Created by Nil Oleart on 19/01/19.
 */
class RatesMapper @Inject constructor() : GeneralMapper<MutableList<Rate>, MutableList<RateDto>>(){
    override fun map(value: MutableList<Rate>): MutableList<RateDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reverseMap(rateDtoList: MutableList<RateDto>): MutableList<Rate> {
        var list = mutableListOf<Rate>()
        for (rateDto in rateDtoList) {
            list.add(addRate(rateDto))
        }
        return list
    }

    private fun addRate(rateDto: RateDto) : Rate =
        Rate(inCurr = rateDto.from,
            outCurr = rateDto.to,
            rate = rateDto.rate.toDouble())
}