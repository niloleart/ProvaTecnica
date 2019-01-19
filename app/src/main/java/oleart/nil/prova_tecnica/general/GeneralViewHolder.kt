package oleart.nil.prova_tecnica.general

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import oleart.nil.prova_tecnica.di.component.ApplicationComponent
import oleart.nil.prova_tecnica.domain.business.BaseItem


abstract class GeneralViewHolder<T : BaseItem> : RecyclerView.ViewHolder {
    var rootView : View

    constructor(itemView: View?) : super(itemView) {
        rootView = itemView as View
    }

    fun addInjections(context: Context) : ApplicationComponent {
        return (context.applicationContext as BaseApplication).getApplicationComponent()
    }

    /**
     * Paint item data
     * position: of item
     * item: is object with all info
     */
    abstract fun decorate(item : T, position : Int)

    /**
     * Modify last item data
     * position: of item
     * item: is object with all info
     */
    open fun modifyLastItem(item : T, position : Int) {

    }

    /**
     * Modify first item data
     * position: of item
     * item: is object with all info
     */
    open fun modifyFirstItem(item : T, position : Int) {

    }

}