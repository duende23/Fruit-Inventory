package com.villadevs.fruitinventory.data

import android.content.ClipData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


/**
 * Database access object to access the Inventory database
 */
@Dao
interface FruitDao {

    @Query("SELECT * from  fruit ORDER BY name ASC")
    fun getFruits(): Flow<List<Fruit>>

    @Query("SELECT * from fruit WHERE id = :id")
    fun getFruit(id: Int): Flow<Fruit>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(fruit: Fruit)

    @Update
    suspend fun update(fruit: Fruit)

    @Delete
    suspend fun delete(fruit: Fruit)
}

