package oleart.nil.prova_tecnica.presentation.main

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import oleart.nil.prova_tecnica.R
import oleart.nil.prova_tecnica.domain.business.products.Product
import oleart.nil.prova_tecnica.domain.business.products.ProductTransaction
import oleart.nil.prova_tecnica.domain.business.rates.Rate
import oleart.nil.prova_tecnica.general.GeneralActivity
import oleart.nil.prova_tecnica.presentation.main.ui.TransactionsAdapter
import java.lang.Exception
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class MainActivity : GeneralActivity(), AdapterView.OnItemSelectedListener {

    val CURRENCY_EU = "EUR"
    @Inject lateinit var mainViewModel: MainViewModel
    private var mRates = mutableListOf<Rate>()
    private var mProductList = mutableListOf<Product>()
    private lateinit var adapter :TransactionsAdapter
    private var manager : LinearLayoutManager = LinearLayoutManager(baseContext)
    var spinnerTitles : MutableList<String> = mutableListOf<String>("Select a Product")
    var spinnerTransactions : MutableList<String> = mutableListOf<String>("")
    var mFirstTime : Boolean = true
    var mSelectedProduct : Product? = null
    var dataAdapter : ArrayAdapter<String>? = null



    override fun initResources() {
        dataAdapter =  SpinnerAdapter(this,  R.layout.spinner_row, spinnerTitles, spinnerTransactions)
        spinner.adapter = dataAdapter
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
            }
        })

        mainViewModel.productList.observe(this, Observer {
            if (it != null) {
                mProductList = it.toMutableList()
                for (product in mProductList) {
                    spinnerTitles.add(product.product)
                    spinnerTransactions.add(product.transaction?.size.toString())
                }
//                dataAdapter = ArrayAdapter<String>(this, R.layout.spinner_row, R.id.spinnerText, spinnerTitles)
                dataAdapter?.notifyDataSetChanged()
                spinner.adapter = dataAdapter
                clicks(false)
            }
        })
    }

    private fun clicks(isFirsTime : Boolean) {
        spinner?.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    mainRV.visibility = View.GONE
                    buttonParentLayout.visibility = View.GONE
                }
                else mainRV.visibility = View.VISIBLE
                if (!isFirsTime) {
                    try {
                        mSelectedProduct= mProductList[position-1]
                        resultText.text =getString(R.string.total) + mSelectedProduct?.transaction?.let { computeResult(it) } + " €"
                        buttonParentLayout.visibility = View.VISIBLE
                    } catch (e : Exception) {
                        mSelectedProduct = null
                    }
                    mSelectedProduct?.transaction?.let { adapter.setList(it) }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                buttonParentLayout.visibility = View.GONE
            }
        }
    }


    //private functions
    private fun setAdapter() {
        mainRV.visibility = View.VISIBLE
        manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL

        mainRV.layoutManager = manager
        adapter = TransactionsAdapter(this)
        mainRV.adapter = adapter
        mSelectedProduct?.transaction?.let { adapter.setList(it) }
    }

    private fun computeResult(transactionList : MutableList<ProductTransaction>) : String {
        var result : Double = 0.0
        for (transaction in transactionList) {
            result += when(transaction.currency) {
                CURRENCY_EU -> roundHalfEven(transaction.amount)
                else-> convert(transaction)
            }
        }
        return roundHalfEven(result).toString()
    }


    private fun convert(transaction: ProductTransaction): Double {
        val euroRates = getAllEuro()
        val euroRatesIn = getAllInCurr(euroRates)
        val ratesIn = getAllInCurr(mRates)

        val conversionRate = mRates.find { it.inCurr == transaction.currency && it.outCurr == CURRENCY_EU }
        if (conversionRate != null) return roundHalfEven( transaction.amount * conversionRate.rate)
        else {
            var interRates = mRates.filter { euroRatesIn.contains(it.outCurr) }
            var firstRate = interRates.find { it.inCurr == transaction.currency }
            if (firstRate != null) {
                var euroRate = euroRates.find { it.inCurr == firstRate.outCurr }
                if (euroRate != null) {
                    return roundHalfEven(roundHalfEven(transaction.amount * euroRate.rate) * firstRate.rate)
                }
            } else {
                var ratesWithTransactionCurrency = mRates.filter { ratesIn.contains(it.inCurr) }
                var firstConversion = ratesWithTransactionCurrency.find {
                    it.inCurr == transaction.currency && getAllInCurr(interRates.toMutableList()).contains(it.outCurr)
                }
                if (firstConversion != null) {
                    var intermediateRate = interRates.find { it.inCurr == firstConversion.outCurr }
                    if (intermediateRate != null) {
                        var lastRate = euroRates.find { it.inCurr == intermediateRate.outCurr }
                        if (lastRate != null) {
                            return roundHalfEven(roundHalfEven(roundHalfEven(transaction.amount * firstConversion.rate) * intermediateRate.rate) * lastRate.rate)
                        } else showToast("cagada")

                    }
                }
            }

        }
        return 0.0
    }


    private fun getAllEuro(): MutableList<Rate> {
        var list = mutableListOf<Rate>()
        for(rate in mRates) {
            if (rate.outCurr == CURRENCY_EU) list.add(rate)
        }
        return list
    }

    private fun getAllInCurr(euroRates : MutableList<Rate>): MutableList<String> {
        var listString = mutableListOf<String>()
        for (rate in euroRates) {
            listString.add(rate.inCurr)
        }
        return listString
    }

    private fun roundHalfEven(double : Double) : Double {
        return BigDecimal(double).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }

    //not used
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}

    override fun onNothingSelected(parent: AdapterView<*>?) {}

}

