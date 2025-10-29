package com.example.creditcardmanager.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.creditcardmanager.model.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Dao
interface SettingsDao {


    @Query("SELECT * from settings order by id DESC limit 1")
    fun getSettings(): Flow<Settings>


    @Update
    suspend fun updateSettings(settings: Settings)

    @Insert
    suspend fun createSettings(settings: Settings)

}