package oleart.nil.prova_tecnica.di.component

import dagger.Component
import oleart.nil.prova_tecnica.presentation.main.MainActivity
import oleart.nil.prova_tecnica.di.module.ApplicationModule
import oleart.nil.prova_tecnica.di.module.NetworkModule
import oleart.nil.prova_tecnica.general.BaseApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
abstract class ApplicationComponent {
    abstract fun inject(application: BaseApplication)
    abstract fun inject(activity: MainActivity)
}