package io.jonibek.currency.data.remote

import io.jonibek.currency.data.remote.json.BaseResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface HttpApi {

    @GET("latest?")
    fun getRates(@Query("base") baseCurrency: String): Observable<BaseResult>

}