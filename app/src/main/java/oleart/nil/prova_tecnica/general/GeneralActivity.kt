package oleart.nil.prova_tecnica.general

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.Toast
import oleart.nil.prova_tecnica.di.component.ApplicationComponent

abstract class GeneralActivity : AppCompatActivity() {

    interface SnackBarButtonListener {
        fun onClickButtonSnackBar()
    }

    fun <T : ViewModel> getViewModel(viewModelFactory : ViewModelProvider.Factory, modelClass: Class<T>): T {
        return ViewModelProviders.of(this, viewModelFactory).get(modelClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        if (getToolbarSpecific() != null){
            setSupportActionBar(getToolbarSpecific())
//            setHasOptionsMenu(true)
        }
        initializeDependencies()
        sendView()
        observeData()
        initResources()
        onClickImplementation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (getOptionsMenuResource() == 0) super.onCreateOptionsMenu(menu)
        else {
            menuInflater.inflate(getOptionsMenuResource(), menu)
            super.onCreateOptionsMenu(menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Template to define a specific toolbar
     */
    protected open fun getToolbarSpecific() : android.support.v7.widget.Toolbar? {
        return null
    }

    /**
     * Template to set the layout ID
     */
    @LayoutRes protected abstract fun getLayoutId() : Int

    /**
     * Template to set texts
     */
    protected abstract fun initResources()

    /**
     * Template to initialize dependencies
     */
    protected open fun initializeDependencies() {

    }

    /**
     * Template to implement clicks
     */
    protected open fun onClickImplementation() {

    }

    /**
     * Template to develop observe data
     */
    protected open fun observeData() {

    }

    /**
     * Template to add menu
     */
    protected open fun getOptionsMenuResource() : Int {
        return 0
    }

    /** Template to enable back button **/
    protected fun enableUpNavigation(enable: Boolean) {
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(enable)
        }
    }

    /**
     * Show default toast with message
     * message : String to show
     */
    fun showToast(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Retrieve the application component
     * @return
     */
    protected fun getApplicationComponent(): ApplicationComponent {
        return (applicationContext as BaseApplication).getApplicationComponent()
    }

    private fun sendView() {
//        if (getAnalyticsScreenName() > 0) baseAnalytics.sendView(getAnalyticsScreenName())
    }
}