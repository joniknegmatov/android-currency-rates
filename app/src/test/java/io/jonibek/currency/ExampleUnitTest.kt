package io.jonibek.currency

import io.jonibek.currency.data.local.CurrencyContainer
import io.jonibek.currency.util.CurrencyHelper
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testCurrencyContainer() {

        val currencyRateMap = mapOf("USD" to 1.411f,
                "EUR" to 1.12f,
                "UZS" to 7800f)

        val currencyContainer = CurrencyContainer("GBP", currencyRateMap)

        assert(currencyContainer.getCurrenciesAmount() == 4)
        assert(currencyContainer.getCurrencyNameAndRate(1).first == "USD")
        assert(currencyContainer.getCurrencyNameAndRate(1).second == 1.411f)
    }

    @Test
    fun testCurrencyHelper() {
       assert(CurrencyHelper.getCurrencyInfo("USD").first == "US Dollar")
       assert(CurrencyHelper.getCurrencyInfo("USD").second == "$")
    }
}
