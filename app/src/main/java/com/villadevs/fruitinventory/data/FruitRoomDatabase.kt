package com.villadevs.fruitinventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton INSTANCE object.
 */
@Database(entities = [Fruit::class], version = 1, exportSchema = false)
abstract class FruitRoomDatabase : RoomDatabase() {

    abstract fun fruitDao(): FruitDao

    companion object {
        @Volatile
        private var INSTANCE: FruitRoomDatabase? = null

        fun getDatabase(context: Context): FruitRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FruitRoomDatabase::class.java,
                    "fruit_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}