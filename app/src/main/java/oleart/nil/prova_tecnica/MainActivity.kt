package oleart.nil.prova_tecnica

import android.arch.lifecycle.Observer
import oleart.nil.prova_tecnica.domain.business.products.Product
import oleart.nil.prova_tecnica.domain.business.rates.Rate
import oleart.nil.prova_tecnica.general.GeneralActivity
import oleart.nil.prova_tecnica.presentation.MainViewModel
import javax.inject.Inject

class MainActivity : GeneralActivity() {
    @Inject lateinit var mainViewModel: MainViewModel
    private var mRates = mutableListOf<Rate>()
    private var mProductList = mutableListOf<Product>()

    override fun initResources() {
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
            }
        })
    }
}
