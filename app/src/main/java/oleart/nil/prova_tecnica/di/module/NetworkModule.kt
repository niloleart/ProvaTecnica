package oleart.nil.prova_tecnica.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import oleart.nil.prova_tecnica.BuildConfig
import oleart.nil.prova_tecnica.data.api.RatesApi
import oleart.nil.prova_tecnica.data.api.TransactionsApi
import oleart.nil.prova_tecnica.di.NamedProperties
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetworkModule {
    private val TIME_OUT : Long = 10 * 10 * 1024 //1 min
    private val NETWORK_CACHE_FOLDER = "http"

    @Provides
    @Singleton
    @Named(NamedProperties.BASE_URL)
    internal fun provideBaseUrl(): String {
        return BuildConfig.WB_BASE_SERVICE
    }

    @Provides
    @Singleton
    internal fun provideCache(application: Application): okhttp3.Cache {
        return Cache(File(application.cacheDir, NETWORK_CACHE_FOLDER), TIME_OUT)
    }

    @Provides
    @Singleton
    fun provideHttpClient(cache : Cache) : OkHttpClient {
        val builder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        builder.addInterceptor(loggingInterceptor)
//        builder.addInterceptor(FixXmlBomInterceptor())
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(10, TimeUnit.SECONDS)
//        builder.cache(cache)
        return builder.build()
    }

    @Provides
    @Singleton
    internal fun provideXmlConverterFactory(): SimpleXmlConverterFactory {
        return SimpleXmlConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@Named(NamedProperties.BASE_URL) baseUrl : String,
                        xmlConverterFactory : SimpleXmlConverterFactory,
                        httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideRatesApi(retrofit: Retrofit): RatesApi {
        return retrofit.create(RatesApi::class.java)
    }

    @Provides
    @Singleton
    internal fun proveTransactionsApi(retrofit: Retrofit) : TransactionsApi {
        return retrofit.create(TransactionsApi::class.java)
    }

}