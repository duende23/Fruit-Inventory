package com.villadevs.fruitinventory

import android.content.ClipData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.villadevs.fruitinventory.data.Fruit
import com.villadevs.fruitinventory.databinding.FragmentAddFruitBinding
import com.villadevs.fruitinventory.databinding.FragmentFruitListBinding
import com.villadevs.fruitinventory.viewmodel.FruitViewModel
import com.villadevs.fruitinventory.viewmodel.FruitViewModelFactory


//private const val ARG_PARAM1 = "param1"


class AddFruitFragment : Fragment() {

    //private var param1: String? = null

    private val viewModel: FruitViewModel by activityViewModels {
        FruitViewModelFactory(
            (activity?.application as InventoryApplication).database.fruitDao()
        )
    }

    lateinit var fruit: Fruit

    private var _binding: FragmentAddFruitBinding? = null
    private val binding get() = _binding!!

    private val args: FruitDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAddFruitBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.fruitId

        if (id > 0) {
            viewModel.retrieveFruit(id).observe(viewLifecycleOwner) { selectedFruit ->
                fruit = selectedFruit
                bind(fruit)
            }
        } else {
            binding.btSaveAction.setOnClickListener {
                addNewItem()
            }
        }

    }


    private fun bind(fruit: Fruit) {
        //val price = "%.2f".format(fruit.itemPrice)
        val price = "%.2f".format(fruit.itemPrice).replace(',', '.')

        binding.apply {
            etItemNname.setText(fruit.itemName, TextView.BufferType.SPANNABLE)
            etItemPrice.setText(price, TextView.BufferType.SPANNABLE)
            etItemCount.setText(fruit.quantityInStock.toString(), TextView.BufferType.SPANNABLE)

            btSaveAction.setOnClickListener { updateItem() }
        }
    }

    /**
     * Inserts the new Item into database and navigates up to list fragment.
     */
    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewItem(
                binding.etItemNname.text.toString(),
                binding.etItemPrice.text.toString(),
                binding.etItemCount.text.toString(),
            )
            val action = AddFruitFragmentDirections.actionAddFruitFragmentToFruitListFragment()
            findNavController().navigate(action)
        }
    }


    private fun updateItem() {
        if (isEntryValid()) {
            viewModel.updateFruit(
                this.args.fruitId,
                this.binding.etItemNname.text.toString(),
                this.binding.etItemPrice.text.toString(),
                this.binding.etItemCount.text.toString()
            )
            val action = AddFruitFragmentDirections.actionAddFruitFragmentToFruitListFragment()
            findNavController().navigate(action)
        }
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.etItemNname.text.toString(),
            binding.etItemPrice.text.toString(),
            binding.etItemCount.text.toString(),
        )
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}