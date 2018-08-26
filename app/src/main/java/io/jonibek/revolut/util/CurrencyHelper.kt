package io.jonibek.revolut.util

import java.util.*


class CurrencyHelper {
    companion object {
        fun getCurrencyInfo(currencyCode : String) : Pair<String?,String?>{
            val currency = Currency.getInstance(currencyCode)
            return Pair(currency.getDisplayName(Locale.ENGLISH),currency.symbol)
        }
    }
}