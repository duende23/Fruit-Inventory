package com.villadevs.fruitinventory

import android.content.ClipData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.villadevs.fruitinventory.data.Fruit
import com.villadevs.fruitinventory.data.getFormattedPrice
import com.villadevs.fruitinventory.databinding.FragmentFruitDetailBinding
import com.villadevs.fruitinventory.viewmodel.FruitViewModel
import com.villadevs.fruitinventory.viewmodel.FruitViewModelFactory


//private const val ARG_PARAM1 = "fruit_id"


class FruitDetailFragment : Fragment() {

    private val viewModel: FruitViewModel by activityViewModels {
        FruitViewModelFactory(
            (activity?.application as InventoryApplication).database.fruitDao()
        )
    }

    lateinit var fruit: Fruit

    private val args: FruitDetailFragmentArgs by navArgs()

    private var _binding: FragmentFruitDetailBinding? = null
    private val binding get() = _binding!!

   //private var fruitId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* arguments?.let {
            fruitId = it.getString(ARG_PARAM1)

        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFruitDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val id = fruitId
        val id = args.fruitId

      /*  viewModel.retrieveItem(id).observe(viewLifecycleOwner) {fruit->
            binding.tvDetailsItemName.text = fruit.itemName
            binding.tvDetailsItemPrice.text = fruit.getFormattedPrice()
            binding.tvDetailsItemCount.text = fruit.quantityInStock.toString()
        }*/

        viewModel.retrieveFruit(id).observe(viewLifecycleOwner) { selectedFruit->
            fruit = selectedFruit
            bind(fruit)
        }

    }

    private fun bind(fruit: Fruit) {
        binding.apply {
            tvDetailsItemName.text = fruit.itemName
            tvDetailsItemPrice.text = fruit.getFormattedPrice()
            tvDetailsItemCount.text = fruit.quantityInStock.toString()

            btSellFruit.isEnabled = viewModel.isStockAvailable(fruit)
            btSellFruit.setOnClickListener { viewModel.sellFruit(fruit) }
            btDeleteFruit.setOnClickListener { showConfirmationDialog() }

            fabEditItem.setOnClickListener { editItem() }

        }


    }

    private fun deleteItem() {
        viewModel.deleteItem(fruit)
        findNavController().navigateUp()
    }


    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }


    private fun editItem() {
        val action = FruitDetailFragmentDirections.actionFruitDetailFragmentToAddFruitFragment(
            getString(R.string.edit_fragment_title),
            fruit.id
        )
        this.findNavController().navigate(action)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}