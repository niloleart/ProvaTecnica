package oleart.nil.prova_tecnica.presentation.main

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import oleart.nil.prova_tecnica.R
import oleart.nil.prova_tecnica.domain.business.products.Product
import oleart.nil.prova_tecnica.domain.business.rates.Rate
import oleart.nil.prova_tecnica.general.GeneralActivity
import oleart.nil.prova_tecnica.presentation.main.ui.TransactionsAdapter
import java.lang.Exception
import javax.inject.Inject

class MainActivity : GeneralActivity(), AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    @Inject lateinit var mainViewModel: MainViewModel
    private var mRates = mutableListOf<Rate>()
    private var mProductList = mutableListOf<Product>()
    private lateinit var adapter :TransactionsAdapter
    private var manager : LinearLayoutManager = LinearLayoutManager(baseContext)
    var spinnerTitles : MutableList<String> = mutableListOf<String>("")
    var mFirstTime : Boolean = true
    var mSelectedProduct : Product? = null


    override fun initResources() {
        setAdapter()
        clicks(mFirstTime)
        mainViewModel.getTransactions()
        mainViewModel.getRates()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initializeDependencies() {
        super.initializeDependencies()
        getApplicationComponent().inject(this)
    }

    override fun observeData() {
        super.observeData()
        mainViewModel.ratesList.observe(this, Observer {
            if (it != null) {
                mRates = it.toMutableList()
//                showToast(mRates[0].outCurr)
            }
        })

        mainViewModel.productList.observe(this, Observer {
            if (it != null) {
                mProductList = it.toMutableList()
                for (product in mProductList) spinnerTitles.add(product.product)
                val dataAdapter = ArrayAdapter<String>(this, R.layout.spinner_row, R.id.spinnerText, spinnerTitles)
                spinner.adapter = dataAdapter
                clicks(false)
            }
        })
    }

    private fun clicks(isFirsTime : Boolean) {
        spinner?.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!isFirsTime) {
                    try {
                        mSelectedProduct= mProductList[position-1]
                    } catch (e : Exception) {
                        mSelectedProduct = null
                    }
                    mSelectedProduct?.product?.let { showToast(it) }
                    mSelectedProduct?.transaction?.let { adapter.setList(it) }
                    adapter.notifyDataSetChanged()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun setAdapter() {
        mainRV.visibility = View.VISIBLE
        manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL

        mainRV.layoutManager = manager
        adapter = TransactionsAdapter(this)
        mainRV.adapter = adapter
        mSelectedProduct?.transaction?.let { adapter.setList(it) }
    }
}
