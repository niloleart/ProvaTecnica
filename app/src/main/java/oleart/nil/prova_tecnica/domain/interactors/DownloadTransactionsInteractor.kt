package oleart.nil.prova_tecnica.domain.interactors

import oleart.nil.prova_tecnica.domain.business.products.Product
import oleart.nil.prova_tecnica.domain.business.transactions.TransactionDto
import oleart.nil.prova_tecnica.domain.business.transactions.TransactionsRepo
import oleart.nil.prova_tecnica.domain.mapper.TransactionsMapper
import oleart.nil.prova_tecnica.general.GeneralWSInteractor
import javax.inject.Inject

/**
 * Created by Nil Oleart on 19/01/19.
 */
class DownloadTransactionsInteractor @Inject constructor(
    var transactionsRepo : TransactionsRepo,
    var transactionsMapper : TransactionsMapper
) : GeneralWSInteractor<MutableList<Product>, MutableList<TransactionDto>>(){

    override fun doAction(listener: LoadDataListener<MutableList<Product>>, listenerError: LoadErrorListener) {
        callWebService(transactionsRepo.getTransactions(), listener, listenerError)
    }

    override fun mapper(data: MutableList<TransactionDto>): MutableList<Product> {
        return transactionsMapper.reverseMap(data)
    }

}