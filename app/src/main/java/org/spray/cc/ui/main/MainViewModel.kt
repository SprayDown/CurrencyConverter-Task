package org.spray.cc.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import org.spray.cc.model.Currency
import org.spray.cc.network.NetworkHelper
import org.spray.cc.utils.coroutineExceptionHandler
import org.spray.cc.utils.getNodeValue
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class MainViewModel(private val url: String)  : ViewModel() {

    val currencyList = MutableLiveData<List<Currency>>()
    val loading = MutableLiveData<Boolean>()

    init {
        loadCurrencyList()
    }

    private fun loadCurrencyList() {
        launch(Dispatchers.Default) {
            val content = NetworkHelper.parseContent(url)

            val builderFactory = DocumentBuilderFactory.newInstance()
            val docBuilder = builderFactory.newDocumentBuilder()
            if (content != null) {
                val doc = docBuilder.parse(InputSource(StringReader(content)))

                val nList = doc.getElementsByTagName("Valute")
                val list = ArrayList<Currency>()

                list += Currency(
                    id = "R00000",
                    charCode = "RUB",
                    name = "Рубль",
                    nominal = 1.0,
                    value = 1.0
                )

                for (i in 0 until nList.length) {
                    if (nList.item(0).nodeType == Node.ELEMENT_NODE) {

                        val element = nList.item(i) as Element

                        list += Currency(
                            id = element.getAttribute("ID"),
                            charCode = element.getNodeValue("CharCode"),
                            name = element.getNodeValue("Name"),
                            nominal = (element.getNodeValue("Nominal").replace(",", ".")
                                .toDoubleOrNull()
                                ?: 1) as Double,
                            value = element.getNodeValue("Value").replace(",", ".").toDouble()
                        )
                    }
                }

                if (list.isNotEmpty())
                    currencyList.postValue(list)

                delay(20)
            }
        }
    }

    private fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context + coroutineExceptionHandler(), start) {
        loading.postValue(true)
        try {
            block()
        } finally {
            loading.postValue(false)
        }
    }

}