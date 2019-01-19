package oleart.nil.prova_tecnica.domain.business.products

/**
 * Created by Nil Oleart on 19/01/19.
 */
data class Product(var product : String, var transaction : MutableList<ProductTransaction>?) {
}