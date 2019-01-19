package oleart.nil.prova_tecnica.domain.business.products

import oleart.nil.prova_tecnica.domain.business.BaseItem

/**
 * Created by Nil Oleart on 19/01/19.
 */
data class ProductTransaction(var amount : Double, var currency : String) : BaseItem()