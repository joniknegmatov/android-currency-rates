package io.jonibek.currency.data.remote.json

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class BaseResult(@SerializedName("base") var base: String,
                      @SerializedName("date") var date: String,
                      @SerializedName("rates") var rates : Map<String,BigDecimal>)