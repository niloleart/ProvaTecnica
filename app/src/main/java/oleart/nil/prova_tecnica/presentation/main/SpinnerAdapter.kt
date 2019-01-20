package oleart.nil.prova_tecnica.presentation.main

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import oleart.nil.prova_tecnica.R

/**
 * Created by Nil Oleart on 20/01/19.
 */
class SpinnerAdapter(context: Context,
                            @LayoutRes private val layoutResource : Int,
                            private val products : List<String>,
                            private val transactionsNum : List<String>)
    : ArrayAdapter<String>(context, layoutResource, products) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent, true)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent, false)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup?, hideSum : Boolean): View{
        val view: LinearLayout = convertView as LinearLayout? ?: LayoutInflater.from(context).inflate(layoutResource, parent, false) as LinearLayout
        var text = view.findViewById<TextView>(R.id.spinnerText)
        var spinnerTextTransactions = view.findViewById<TextView>(R.id.spinnerTextTransactions)
        text.text = products[position]
        if (position == 0) spinnerTextTransactions.text = "(transactions)"
        else spinnerTextTransactions.text = "(" + transactionsNum[position] + ")"
        if (hideSum) smallView(spinnerTextTransactions)

        return view
    }

    private fun smallView(textView: TextView) {
        textView.visibility = View.GONE
    }
}