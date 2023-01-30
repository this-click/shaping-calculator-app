package com.example.shapingcalculator.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShapedItem::class], version = 1, exportSchema = false)
abstract class ShapedItemRoomDatabase: RoomDatabase() {
    abstract fun shapedItemDao(): ShapedItemDao

    companion object {
        @Volatile
        private var INSTANCE: ShapedItemRoomDatabase? = null

        fun getDatabase(context: Context): ShapedItemRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShapedItemRoomDatabase::class.java,
                    "shaped_item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
