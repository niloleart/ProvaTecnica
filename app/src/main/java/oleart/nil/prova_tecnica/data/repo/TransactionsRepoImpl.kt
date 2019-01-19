package oleart.nil.prova_tecnica.data.repo

import oleart.nil.prova_tecnica.data.api.TransactionsApi
import oleart.nil.prova_tecnica.domain.business.transactions.Transaction
import oleart.nil.prova_tecnica.domain.business.transactions.TransactionDto
import oleart.nil.prova_tecnica.domain.business.transactions.TransactionsRepo
import retrofit2.Call
import javax.inject.Inject

/**
 * Created by Nil Oleart on 19/01/19.
 */
class TransactionsRepoImpl @Inject constructor(var transactionsApi: TransactionsApi) : TransactionsRepo {
    override fun getTransactions(): Call<MutableList<TransactionDto>> {
        return transactionsApi.getTransactions()
    }

    override fun saveRates(list: MutableList<Transaction>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}