package com.rodion2236.loftmoney.remote.authApi

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {

    @GET("./auth")
    fun makeLogin(@Query("id_user") UserId: String?): Single<AuthResponse?>?
}