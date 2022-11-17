package org.spray.cc.ui.converter

import androidx.lifecycle.ViewModel
import org.spray.cc.model.Currency
import java.math.RoundingMode

class ConverterViewModel : ViewModel() {

    lateinit var inputCurrency: Currency
    lateinit var outputCurrency: Currency

    fun setCurrency(input: Currency, output: Currency) {
        inputCurrency = input
        outputCurrency = output
    }

    fun reverse() {
        val prevInput = inputCurrency
        inputCurrency = outputCurrency
        outputCurrency = prevInput
    }

    /**
     * @return Получаем законвертированную валюту для outputCurrency
     */
    fun convertToOutput(value: Float): String {
        val calc = if (outputCurrency.id == "R00000") { // Если находим рубль.
            inputCurrency.value * value
        } else {
            val i = (inputCurrency.value * value)
            val out = (outputCurrency.value / outputCurrency.nominal)
            i / out
        }
        return calc.toBigDecimal()
            .setScale(2, RoundingMode.UP).toDouble().toString()
    }

    fun convertToInput(value: Float): String {
        val calc = if (inputCurrency.id == "R00000") {
            outputCurrency.value * value
        } else {
            val i = (outputCurrency.value * value)
            val out = (inputCurrency.value / inputCurrency.nominal)
            i / out
        }
        return calc.toBigDecimal()
            .setScale(2, RoundingMode.UP).toDouble().toString()
    }
}