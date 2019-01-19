package oleart.nil.prova_tecnica.domain.mapper

import oleart.nil.prova_tecnica.domain.business.transactions.Transaction
import oleart.nil.prova_tecnica.domain.business.transactions.TransactionDto
import oleart.nil.prova_tecnica.general.GeneralMapper
import javax.inject.Inject

/**
 * Created by Nil Oleart on 19/01/19.
 */
class TransactionsMapper @Inject  constructor() : GeneralMapper<MutableList<Transaction>, MutableList<TransactionDto>>(){
    override fun map(value: MutableList<Transaction>): MutableList<TransactionDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reverseMap(transactionDtoList: MutableList<TransactionDto>): MutableList<Transaction> {
        var list = mutableListOf<Transaction>()
        for (transactionDto in transactionDtoList) {
            list.add(convertTransactionDtoToTransaction(transactionDto))
        }
        return list
    }

    private fun convertTransactionDtoToTransaction(transactionDto: TransactionDto) : Transaction =
            Transaction(product = transactionDto.sku,
                amount = transactionDto.amount.toDouble(),
                currency = transactionDto.currency)
}