package com.example.creditcardmanager.repository

import com.example.creditcardmanager.data.SettingsDao
import com.example.creditcardmanager.model.Settings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val settingsDao: SettingsDao) {


    fun getSettings(): Flow<Settings> = settingsDao.getSettings()

    suspend fun updateSettings(settings: Settings) = settingsDao.updateSettings(settings)

    suspend fun createSettings(settings: Settings) = settingsDao.createSettings(settings)
}