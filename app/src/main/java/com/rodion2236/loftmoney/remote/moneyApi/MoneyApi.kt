package com.rodion2236.loftmoney.remote.moneyApi

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface MoneyApi {

    @GET("./items")
    fun getmoneyItems(@Query("type") type: String?, @Query("auth-token")authToken: String?):
            Single<List<MoneyRemoteItem>>

    @POST("./items/add")
    fun addItem(
        @Field("price") price: Int,
        @Field("name") name: String?,
        @Field("type") type: String?,
        @Field("auth-token") token: String?
    ): Completable?

    @POST("./items/remove")
    fun remove(
        @Field("id") id: Int,
        @Field("auth-token") token: String?
    ): Completable?

    @GET("./balance")
    fun getBalance(@Query("auth-token") token: String?): Single<BalanceResponse?>?
}