package oleart.nil.prova_tecnica.presentation.main

import oleart.nil.prova_tecnica.domain.business.products.ProductTransaction
import oleart.nil.prova_tecnica.domain.business.rates.Rate
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Created by Nil Oleart on 20/01/19.
 */
class MainUtils(var mRates : MutableList<Rate>) {

    val CURRENCY_EU = "EUR"

    private fun computeResult(transactionList : MutableList<ProductTransaction>) : String {
        var result : Double = 0.0
        for (transaction in transactionList) {
            result += when(transaction.currency) {
                CURRENCY_EU -> roundHalfEven(transaction.amount)
                else-> convert(transaction)
            }
        }
        return roundHalfEven(result).toString()
    }


    fun convert(transaction: ProductTransaction): Double {
        val euroRates = getAllEuro()
        val euroRatesIn = getAllInCurr(euroRates)
        val ratesIn = getAllInCurr(mRates)

        val conversionRate = mRates.find { it.inCurr == transaction.currency && it.outCurr == CURRENCY_EU }
        if (conversionRate != null) return roundHalfEven( transaction.amount * conversionRate.rate)
        else {
            var interRates = mRates.filter { euroRatesIn.contains(it.outCurr) }
            var firstRate = interRates.find { it.inCurr == transaction.currency }
            if (firstRate != null) {
                var euroRate = euroRates.find { it.inCurr == firstRate.outCurr }
                if (euroRate != null) {
                    return roundHalfEven(roundHalfEven(transaction.amount * euroRate.rate) * firstRate.rate)
                }
            } else {
                var ratesWithTransactionCurrency = mRates.filter { ratesIn.contains(it.inCurr) }
                var firstConversion = ratesWithTransactionCurrency.find {
                    it.inCurr == transaction.currency && getAllInCurr(interRates.toMutableList()).contains(it.outCurr)
                }
                if (firstConversion != null) {
                    var intermediateRate = interRates.find { it.inCurr == firstConversion.outCurr }
                    if (intermediateRate != null) {
                        var lastRate = euroRates.find { it.inCurr == intermediateRate.outCurr }
                        if (lastRate != null) {
                            return roundHalfEven(roundHalfEven(roundHalfEven(transaction.amount * firstConversion.rate) * intermediateRate.rate) * lastRate.rate)
                        }
                    }
                }
            }

        }
        return 0.0
    }

    fun getAllEuro(): MutableList<Rate> {
        var list = mutableListOf<Rate>()
        for(rate in mRates) {
            if (rate.outCurr == CURRENCY_EU) list.add(rate)
        }
        return list
    }

    private fun getAllInCurr(euroRates : MutableList<Rate>): MutableList<String> {
        var listString = mutableListOf<String>()
        for (rate in euroRates) {
            listString.add(rate.inCurr)
        }
        return listString
    }

    private fun roundHalfEven(double : Double) : Double {
        return BigDecimal(double).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }
}