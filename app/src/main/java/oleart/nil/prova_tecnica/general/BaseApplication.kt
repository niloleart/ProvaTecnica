package oleart.nil.prova_tecnica.general

import android.app.Application
import oleart.nil.prova_tecnica.di.component.ApplicationComponent
import oleart.nil.prova_tecnica.di.component.DaggerApplicationComponent
import oleart.nil.prova_tecnica.di.module.ApplicationModule

class BaseApplication : Application() {
    private lateinit var applicationComponent : ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        configureDependencies()
    }
    private fun configureDependencies() {
        applicationComponent = buildApplicationComponent()
        applicationComponent.inject(this)
    }

    fun buildApplicationComponent(): ApplicationComponent {
        //TODO Resolve dep
        return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    /**
     * Retrieve the application component
     *
     * @return @{link ApplicationComponent}
     */
    fun getApplicationComponent(): ApplicationComponent {
        return applicationComponent
    }

}