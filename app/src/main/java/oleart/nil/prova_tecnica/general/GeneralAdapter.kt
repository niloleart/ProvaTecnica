package oleart.nil.prova_tecnica.general

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import oleart.nil.prova_tecnica.domain.business.BaseItem

open abstract class GeneralAdapter<T : BaseItem> : RecyclerView.Adapter<GeneralViewHolder<T>>(){
    var listData : MutableList<T> = mutableListOf()

    open fun setList(list : MutableList<T>){
        listData.clear()
        listData.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: GeneralViewHolder<T>, position: Int) {
        holder.decorate(listData[position], position)
        if (position == 0) holder.modifyFirstItem(listData[position], position)
        if (position == listData.size -1) holder.modifyLastItem(listData[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralViewHolder<T> {
        return getViewHolder(parent, viewType)
    }

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): GeneralViewHolder<T>
}