package org.spray.cc.ui.selection

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import org.spray.cc.R
import org.spray.cc.databinding.FragmentCurrencySelectionBinding
import org.spray.cc.ui.main.MainActivity

class SelectionFragment : Fragment(),
    SelectionAdapter.OnSelectedListener {

    private lateinit var binding: FragmentCurrencySelectionBinding

    private val adapter: SelectionAdapter by lazy {
        SelectionAdapter(selectedIndex = selectedCurrency, listener = this)
    }

    private var selectedCurrency: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencySelectionBinding.inflate(inflater, container, false)

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
        }

        adapter.setDataSet((requireActivity() as MainActivity).currencyList)

        initOptionsMenu()

        return binding.root
    }

    private fun initOptionsMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_selection, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_cancel -> {
                        requireActivity().onBackPressed()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onSelect(position: Int) {
        requireActivity().onBackPressed()
    }

}