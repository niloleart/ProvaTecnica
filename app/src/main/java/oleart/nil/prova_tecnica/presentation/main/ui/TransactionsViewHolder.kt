package oleart.nil.prova_tecnica.presentation.main.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import oleart.nil.prova_tecnica.R
import oleart.nil.prova_tecnica.domain.business.products.ProductTransaction
import oleart.nil.prova_tecnica.general.GeneralViewHolder

/**
 * Created by Nil Oleart on 19/01/19.
 */
class TransactionsViewHolder(var view : View) : GeneralViewHolder<ProductTransaction>(view) {
    var amount = view.findViewById<TextView>(R.id.itemAmount)
    var currency = view.findViewById<TextView>(R.id.itemCurrency)
    companion object {
        fun createViewHolder(parent: ViewGroup?) : GeneralViewHolder<ProductTransaction> {
            return TransactionsViewHolder(LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_transaction, parent, false))
        }
    }

    override fun decorate(item: ProductTransaction, position: Int) {
        currency.text = item.currency
        amount.text = item.amount.toString()
    }

}