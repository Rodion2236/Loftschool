package com.rodion2236.loftmoney.api

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface MoneyApi {

    @GET("./items")
    fun getmoneyItems(@Query("type") type: String?, authToken: String?):
            Single<List<MoneyItemsResponse?>?>?

    @POST("./items/add")
    @FormUrlEncoded
    fun addItem(
        @Field("price") price: Int,
        @Field("name") name: String?,
        @Field("type") type: String?,
        @Field("auth-token") token: String?
    ): Completable?
}