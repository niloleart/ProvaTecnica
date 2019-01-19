package oleart.nil.prova_tecnica.data.api

import oleart.nil.prova_tecnica.domain.business.transactions.TransactionDto
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Nil Oleart on 19/01/19.
 */
interface TransactionsApi {

    @GET("/transactions.json")
    fun getTransactions() : Call<MutableList<TransactionDto>>
}