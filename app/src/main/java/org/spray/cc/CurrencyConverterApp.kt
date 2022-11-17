package org.spray.cc

import android.app.Application
import org.spray.cc.network.NetworkHelper

class CurrencyConverterApp : Application() {

    override fun onCreate() {
        super.onCreate()

        NetworkHelper.init()
    }
}