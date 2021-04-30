package io.github.mattpvaughn.uigradients.injection.modules

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import io.github.mattpvaughn.uigradients.BuildConfig
import io.github.mattpvaughn.uigradients.application.APP_NAME
import io.github.mattpvaughn.uigradients.application.LOG_NETWORK_REQUESTS
import io.github.mattpvaughn.uigradients.data.local.GradientDao
import io.github.mattpvaughn.uigradients.data.local.GradientDatabase
import io.github.mattpvaughn.uigradients.data.local.PrefsRepo
import io.github.mattpvaughn.uigradients.data.local.SharedPreferencesPrefsRepo
import io.github.mattpvaughn.uigradients.data.local.model.Converters
import io.github.mattpvaughn.uigradients.data.remote.BASE_URL
import io.github.mattpvaughn.uigradients.data.remote.UIGradientsApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
        app.getSharedPreferences(APP_NAME, MODE_PRIVATE)

    @Provides
    @Singleton
    fun loggingInterceptor() = if (BuildConfig.DEBUG && LOG_NETWORK_REQUESTS) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    @Provides
    @Singleton
    fun moshi() = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideTypeConverters(moshi: Moshi): Converters = Converters(moshi)

    private val MODEL_DATABASE_NAME = "model_db"

    @Provides
    @Singleton
    fun provideGradientDatabase(converters: Converters): GradientDatabase = Room.databaseBuilder(
        app.applicationContext, GradientDatabase::class.java, MODEL_DATABASE_NAME
    ).addTypeConverter(converters).build()

    @Provides
    @Singleton
    fun provideModelDao(gradientDatabase: GradientDatabase): GradientDao =
        gradientDatabase.gradientDao


    @Provides
    @Singleton
    fun okHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS).addInterceptor(loggingInterceptor).build()

    @Provides
    @Singleton
    fun retrofit(
        okHttpClient: OkHttpClient, moshi: Moshi
    ): Retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient).baseUrl(BASE_URL).build()

    @Provides
    @Singleton
    fun gradientsService(retrofit: Retrofit): UIGradientsApi =
        retrofit.create(UIGradientsApi::class.java)

    @Provides
    @Singleton
    fun providePrefsRepo(prefsImpl: SharedPreferencesPrefsRepo): PrefsRepo = prefsImpl

    @Provides
    @Singleton
    fun backgroundDispatcher(): CoroutineDispatcher = Dispatchers.IO
}