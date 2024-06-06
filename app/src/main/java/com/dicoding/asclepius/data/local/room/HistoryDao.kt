package com.dicoding.asclepius.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.local.entity.HistoryEntity

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(historyEntity: HistoryEntity)

    @Query("Delete from historyentity where image = :image")
    fun delete(image: String)

    @Query("Select * from historyentity")
    fun getFavoriteUser(): LiveData<List<HistoryEntity>>

    @Query("Select * from historyentity where image = :image")
    fun getBookmarkResultByImage(image: String): LiveData<HistoryEntity>
}