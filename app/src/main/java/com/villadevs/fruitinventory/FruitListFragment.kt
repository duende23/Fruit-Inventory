package com.villadevs.fruitinventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.villadevs.fruitinventory.adpater.FruitListAdapter
import com.villadevs.fruitinventory.databinding.FragmentFruitListBinding
import com.villadevs.fruitinventory.viewmodel.FruitViewModel
import com.villadevs.fruitinventory.viewmodel.FruitViewModelFactory
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"


class FruitListFragment : Fragment() {

    private val viewModel: FruitViewModel by activityViewModels {
        FruitViewModelFactory(
            (activity?.application as InventoryApplication).database.fruitDao()
        )
    }

    private var _binding: FragmentFruitListBinding? = null
    private val binding get() = _binding!!

    private var param1: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFruitListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FruitListAdapter {
            val action = FruitListFragmentDirections.actionFruitListFragmentToFruitDetailFragment(it.id)
            this.findNavController().navigate(action)

        }
        binding.recyclerView.adapter = adapter
        viewModel.allFruits.observe(this.viewLifecycleOwner) { fruits ->
            fruits.let {
                adapter.submitList(it)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)



        binding.floatingActionButton.setOnClickListener {
            val action = FruitListFragmentDirections.actionFruitListFragmentToAddFruitFragment(
                getString(R.string.add_fragment_title)
            )
            this.findNavController().navigate(action)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}