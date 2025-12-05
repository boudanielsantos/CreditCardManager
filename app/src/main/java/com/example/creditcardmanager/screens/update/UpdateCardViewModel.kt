package com.example.creditcardmanager.screens.update

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.creditcardmanager.data.DataOrException
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.repository.CreditCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@HiltViewModel
class UpdateCardViewModel @Inject constructor(
    private val cardRepository: CreditCardRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _creditCard =
        MutableStateFlow<DataOrException<CreditCard, Boolean, Exception>>(
            DataOrException(
                null,
                true,
                Exception("")
            )
        )
    val creditCard = _creditCard.asStateFlow()


    init {
        viewModelScope.launch {
            val cardId: Int? = savedStateHandle["cardId"]

            try {
                cardId?.let {
                    cardRepository.getCardById(it).distinctUntilChanged().collect { cardFromDb ->
                        _creditCard.value =
                            DataOrException(data = cardFromDb, loading = false, exception = null)
                    }
                }
            } catch (e: Exception) {
                _creditCard.value = DataOrException(
                    data = null,
                    loading = false,
                    exception = e
                )
            }

        }

    }

    fun updateCard(creditCard: CreditCard) {
        viewModelScope.launch { cardRepository.updateCreditCard(creditCard) }
    }
}