package com.villadevs.fruitinventory.adpater


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.villadevs.fruitinventory.data.Fruit
import com.villadevs.fruitinventory.data.getFormattedPrice
import com.villadevs.fruitinventory.databinding.FruitListItemBinding


class FruitListAdapter(val onItemClicked: (Fruit) -> Unit) :
    ListAdapter<Fruit, FruitListAdapter.FruitViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        val viewHolder = FruitViewHolder(
            FruitListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        /* viewHolder.itemView.setOnClickListener {
             val position = viewHolder.adapterPosition
             onItemClicked(getItem(position))
         }*/
        return viewHolder
    }

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {

        val currentFruit = getItem(position)
        holder.bind(currentFruit)

        holder.itemView.setOnClickListener {
            onItemClicked(currentFruit)
        }
    }


    class FruitViewHolder(private var binding: FruitListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fruit: Fruit) {
            /* binding.tvItemName.text = fruit.itemName
             binding.tvItemPrice.text = fruit.getFormattedPrice()
             binding.tvItemQuantity.text = fruit.quantityInStock.toString()*/

            binding.apply {
                tvItemName.text = fruit.itemName
                tvItemPrice.text = fruit.getFormattedPrice()
                tvItemQuantity.text = fruit.quantityInStock.toString()
            }

        }


    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Fruit>() {
            override fun areItemsTheSame(oldItem: Fruit, newItem: Fruit): Boolean {
                //return oldItem.id == newItem.id
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Fruit, newItem: Fruit): Boolean {
                //return oldItem == newItem
                return oldItem.itemName == newItem.itemName
            }
        }
    }

}