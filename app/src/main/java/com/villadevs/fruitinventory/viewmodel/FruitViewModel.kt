package com.villadevs.fruitinventory.viewmodel

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.villadevs.fruitinventory.data.Fruit
import com.villadevs.fruitinventory.data.FruitDao
import kotlinx.coroutines.launch

class FruitViewModel(private val fruitDao: FruitDao):ViewModel(){

    //val allFruits: Flow<List<Fruit>> = fruitDao.getFruits()
    val allFruits: LiveData<List<Fruit>> = fruitDao.getFruits().asLiveData()


    /**
     * Updates an existing Item in the database.
     */

    fun updateFruit(itemId: Int, itemName: String, itemPrice: String, itemCount: String) {
        val updatedFruit = getUpdatedFruitEntry(itemId, itemName, itemPrice, itemCount)
        updateFruit(updatedFruit)
    }


    /**
     * Launching a new coroutine to update an item in a non-blocking way
     */

    private fun updateFruit(fruit: Fruit) {
        viewModelScope.launch {
            fruitDao.update(fruit)
        }
    }

    /**
     * Inserts the new Item into database.
     */
    fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
        insertItem(newItem)
    }

    /**
     * Launching a new coroutine to insert an item in a non-blocking way
     */
    private fun insertItem(fruit: Fruit) {
        viewModelScope.launch {
            fruitDao.insert(fruit)
        }
    }


    /**
     * Launching a new coroutine to delete an item in a non-blocking way
     */
      fun deleteItem(fruit: Fruit) {
        viewModelScope.launch {
            fruitDao.delete(fruit)
        }
    }

    /**
     * Retrieve an item from the repository.
     */

    fun retrieveFruit(id: Int): LiveData<Fruit> {
        return fruitDao.getFruit(id).asLiveData()
    }



    fun sellFruit(fruit: Fruit) {
        if (fruit.quantityInStock > 0) {
            // Decrease the quantity by 1
            val newFruit = fruit.copy(quantityInStock = fruit.quantityInStock - 1)
            updateFruit(newFruit)
        }
    }

    fun isStockAvailable(fruit: Fruit): Boolean {
        return (fruit.quantityInStock > 0)
    }


    /**
     * Returns true if the EditTexts are not empty
     */
    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }


    /**
     * Returns an instance of the [Item] entity class with the item info entered by the user.
     * This will be used to add a new entry to the Inventory database.
     */
    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Fruit {
        return Fruit(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }


    private fun getUpdatedFruitEntry(itemId: Int, itemName: String, itemPrice: String, itemCount: String): Fruit {
        return Fruit(id = itemId, itemName = itemName, itemPrice = itemPrice.toDouble(), quantityInStock = itemCount.toInt())
    }

}