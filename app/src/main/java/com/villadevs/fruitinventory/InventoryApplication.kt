package com.villadevs.fruitinventory

import android.app.Application
import com.villadevs.fruitinventory.data.FruitRoomDatabase

class InventoryApplication:Application (){
    val database: FruitRoomDatabase by lazy { FruitRoomDatabase.getDatabase(this) }
}