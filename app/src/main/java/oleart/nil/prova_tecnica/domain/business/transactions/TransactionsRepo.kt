package oleart.nil.prova_tecnica.domain.business.transactions

import retrofit2.Call

/**
 * Created by Nil Oleart on 19/01/19.
 */
interface TransactionsRepo {
    fun getTransactions() : Call<MutableList<TransactionDto>>

    fun saveRates(list: MutableList<Transaction>)
}