package io.jonibek.revolut.data.remote

import io.jonibek.revolut.data.remote.json.BaseResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface HttpApi {

    @GET("latest?")
    fun getRates(@Query("base") baseCurrency: String): Observable<BaseResult>

}