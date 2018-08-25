package io.jonibek.revolut.data.local


class CurrencyContainer(base: String, ratesFromServer: Map<String, Float>) {

    private var rates = mutableMapOf<String,Float>()
    private var currencyNameList = arrayListOf<String>()

    init {
        rates.putAll(ratesFromServer)
        rates[base] = 0f
        currencyNameList.add(base)
        currencyNameList.addAll(rates.keys)
    }

    fun updateValues(newRates : Map<String,Float>){
        rates.putAll(newRates)
    }

    fun moveCurrencyToTop(currencyCode: String) : Int{
        val index = currencyNameList.indexOf(currencyCode)
        currencyNameList.remove(currencyCode)
        currencyNameList.add(0,currencyCode)
        return index
    }

    fun getCurrencyName(index : Int) : Pair<String,Float?>{
        return Pair( currencyNameList[index], rates[currencyNameList[index]])
    }

    fun getCurrenciesAmount() : Int {
        return currencyNameList.size
    }

}