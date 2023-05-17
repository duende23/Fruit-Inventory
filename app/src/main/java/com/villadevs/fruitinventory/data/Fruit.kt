package com.villadevs.fruitinventory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

/**
 * Entity data class represents a single row in the database.
 */
@Entity
data class Fruit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val itemName: String,
    @ColumnInfo(name = "price") val itemPrice: Double,
    @ColumnInfo(name = "quantity") val quantityInStock: Int,
)


fun Fruit.getFormattedPrice(): String =
    NumberFormat.getCurrencyInstance().format(itemPrice)