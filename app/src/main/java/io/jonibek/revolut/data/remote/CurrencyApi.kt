package io.jonibek.revolut.data.remote

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory



class CurrencyApi {

    companion object {
        private const val BASE_URL = "https://revolut.duckdns.org/"

        private var retrofit : Retrofit ? = null

        fun getClient() : Retrofit ? {

            if(retrofit == null){
                retrofit = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .baseUrl(BASE_URL)
                        .build()
            }

            return retrofit
        }

    }


}