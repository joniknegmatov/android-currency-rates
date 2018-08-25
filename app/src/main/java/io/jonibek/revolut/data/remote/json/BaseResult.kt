package io.jonibek.revolut.data.remote.json

import com.google.gson.annotations.SerializedName

data class BaseResult(@SerializedName("base") var base: String,
                      @SerializedName("date") var date: String,
                      @SerializedName("rates") var rates : Map<String,Float>)