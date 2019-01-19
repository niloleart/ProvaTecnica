package oleart.nil.prova_tecnica.presentation.main.ui

import android.content.Context
import android.view.ViewGroup
import oleart.nil.prova_tecnica.domain.business.products.ProductTransaction
import oleart.nil.prova_tecnica.general.GeneralAdapter
import oleart.nil.prova_tecnica.general.GeneralViewHolder

/**
 * Created by Nil Oleart on 19/01/19.
 */
class TransactionsAdapter(var context: Context) : GeneralAdapter<ProductTransaction>() {
    override fun getViewHolder(parent: ViewGroup, viewType: Int): GeneralViewHolder<ProductTransaction> {
        return TransactionsViewHolder.createViewHolder(parent)
    }
}