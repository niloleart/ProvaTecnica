package oleart.nil.prova_tecnica.domain.mapper

import oleart.nil.prova_tecnica.domain.business.products.Product
import oleart.nil.prova_tecnica.domain.business.products.ProductTransaction
import oleart.nil.prova_tecnica.domain.business.transactions.Transaction
import oleart.nil.prova_tecnica.domain.business.transactions.TransactionDto
import oleart.nil.prova_tecnica.general.GeneralMapper
import javax.inject.Inject

/**
 * Created by Nil Oleart on 19/01/19.
 */
class TransactionsMapper @Inject  constructor() : GeneralMapper<MutableList<Product>, MutableList<TransactionDto>>(){
    override fun map(value: MutableList<Product>): MutableList<TransactionDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reverseMap(transactionDtoList: MutableList<TransactionDto>): MutableList<Product> {
        var productList = mutableListOf<Product>()
        for (transactionDto in transactionDtoList) {
//            productList.add(convertTransactionDtoToTransaction(transactionDto))
            if (transactionDto.sku !in productList.map { it.product })
                productList.add(Product(transactionDto.sku, mutableListOf<ProductTransaction>()))
        }
        addAllTransactionsToProduct(productList, transactionDtoList)

//        if (checkResult(productList, transactionDtoList.size)){
//
//        } else {   }

        return productList
    }

    private fun convertTransactionDtoToTransaction(transactionDto: TransactionDto) : Transaction =
        Transaction(product = transactionDto.sku,
            amount = transactionDto.amount.toDouble(),
            currency = transactionDto.currency)

    private fun addAllTransactionsToProduct(productList: MutableList<Product>, transactionDtoList: MutableList<TransactionDto>) : MutableList<Product>{
        for (transaction in transactionDtoList) {
            for (product in productList) {
                if (transaction.sku == product.product) {
                    product.transaction?.add(ProductTransaction(transaction.amount.toDouble(), transaction.currency))
                }
            }
        }
        return productList
    }


    //TODO delete
    private fun checkResult(productList : MutableList<Product>, dtoSize : Int) : Boolean {
        var size: Int = 0
        for (product in productList) {
            size += product.transaction!!.size
        }
        return size == dtoSize
    }
}