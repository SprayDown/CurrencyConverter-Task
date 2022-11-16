package org.spray.cc.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import org.spray.cc.R
import org.spray.cc.databinding.ActivityMainBinding
import org.spray.cc.model.Currency

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    val currencyList = ArrayList<Currency>()

    private val viewModel by lazy {
        ViewModelProvider(this, MainModelFactory(URL))[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.converterFragment, R.id.currencySelectionFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        viewModel.loading.observe(this, ::setLoading)

        viewModel.currencyList.observe(this) { list ->
            currencyList.clear()
            currencyList.addAll(list)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun setLoading(state: Boolean) = with(binding) {
        frameProgressbar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    companion object {

        const val URL = "https://www.cbr.ru/scripts/XML_daily.asp"

    }
}