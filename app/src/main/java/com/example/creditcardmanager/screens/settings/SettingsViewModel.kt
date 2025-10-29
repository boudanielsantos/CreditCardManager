package com.example.creditcardmanager.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.creditcardmanager.data.DataOrException
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.model.Settings
import com.example.creditcardmanager.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsRepository: SettingsRepository) :
    ViewModel() {
    private val _settings = MutableStateFlow<DataOrException<Settings, Boolean, Exception>>(
        DataOrException(null, true, Exception(""))
    )
    val settings = _settings.asStateFlow()


    init {
        viewModelScope.launch {
            try {
                settingsRepository.getSettings().distinctUntilChanged()
                    .collect { settingsFromDb ->
                        _settings.value.loading = true
                        _settings.value = _settings.value.copy(
                            data = settingsFromDb,
                            loading = false,
                            exception = null

                        )
                    }
            } catch (e: Exception) {
                _settings.value = _settings.value.copy(data = null, loading = false, exception = e)
            }

        }
    }

    fun updateSettings(settings: Settings) =
        viewModelScope.launch {
            settingsRepository.updateSettings(settings)
        }

    fun createSettings(settings: Settings) =
        viewModelScope.launch {
            settingsRepository.createSettings(settings)
        }
}