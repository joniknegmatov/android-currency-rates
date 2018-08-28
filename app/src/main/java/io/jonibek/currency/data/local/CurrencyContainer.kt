package io.jonibek.currency.data.local

import java.math.BigDecimal


class CurrencyContainer(base: String, ratesFromServer: Map<String, BigDecimal>) {

    private var rates = mutableMapOf<String, BigDecimal>()
    private var currencyNameList = arrayListOf<String>()

    init {
        rates[base] = BigDecimal.ZERO
        rates.putAll(ratesFromServer)
        currencyNameList.addAll(rates.keys)
    }

    fun updateValues(newRates : Map<String,BigDecimal>){
        rates.putAll(newRates)
    }

    fun moveCurrencyToTop(currencyCode: String) : Int{
        val index = currencyNameList.indexOf(currencyCode)
        currencyNameList.remove(currencyCode)
        currencyNameList.add(0,currencyCode)
        return index
    }

    fun changeBaseRate(currencyName: String, rate : BigDecimal){
        rates[currencyName] = rate
    }

    fun getCurrencyNameAndRate(index : Int) : Pair<String,BigDecimal?>{
        return Pair( currencyNameList[index], rates[currencyNameList[index]])
    }

    fun getCurrenciesAmount() : Int {
        return currencyNameList.size
    }

}