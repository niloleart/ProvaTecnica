package oleart.nil.prova_tecnica.di

class NamedProperties {

    init {
        throw AssertionError("NamedProperties can't be instantiated")
    }

    companion object {
        const val BASE_URL = "NamedBaseUrl"
        const val RETROFIT_BASE = "RetrofitBase"
    }
}