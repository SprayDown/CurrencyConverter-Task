package org.spray.cc.ui.selection

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import org.spray.cc.R
import org.spray.cc.databinding.FragmentCurrencySelectionBinding
import org.spray.cc.ui.converter.ConverterFragment
import org.spray.cc.ui.main.MainActivity

class SelectionFragment : Fragment(),
    OnSelectedListener, MenuProvider {

    private lateinit var binding: FragmentCurrencySelectionBinding

    private val args: SelectionFragmentArgs by navArgs()

    private val adapter: SelectionAdapter by lazy {
        SelectionAdapter(selectedId = args.selectedId, listener = this)
    }

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

        // Options Menu
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

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

    override fun onSelect(id: String) {
        setFragmentResult(
            ConverterFragment.RESULT_FROM_SELECTION, bundleOf(
                "id" to id,
                "currencyIndex" to args.currencyIndex // Input[0] & Output[1] index
            )
        )
        findNavController().navigateUp()
    }
}