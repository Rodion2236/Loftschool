package com.rodion2236.loftmoney

import android.app.Application
import com.rodion2236.loftmoney.remote.authApi.AuthApi
import com.rodion2236.loftmoney.remote.moneyApi.MoneyApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class LoftApp: Application() {

    lateinit var moneyApi: MoneyApi
    lateinit var authApi: AuthApi

    companion object {
        const val AUTH_KEY = "authKey"
    }

    override fun onCreate() {
        super.onCreate()

        configureRetrofit()
    }

    private fun configureRetrofit() {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okhttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://loftschool.com/android-api/basic/v1/")
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        moneyApi = retrofit.create(MoneyApi::class.java)
        authApi = retrofit.create(AuthApi::class.java)
    }
}