package io.jonibek.revolut.data.local

class CurrencyContainer{

    private var rates = mutableMapOf<String,Float>()

    constructor(base : String, rates : Map<String,Float>){
        this.rates.putAll(rates)
        this.rates[base] = 0f
        rates.size
    }

    fun updateValues(newRates : Map<String,Float>){
        rates.putAll(newRates)
    }

    fun getRate(key : String) : Float? {
       return rates[key]
    }


}