package com.example.shapingcalculator.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ShapedItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShapedItem)

    @Query("SELECT * FROM shaped_item")
    fun getItem(): Flow<ShapedItem>

}