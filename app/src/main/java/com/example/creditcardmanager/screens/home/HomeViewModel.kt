package com.example.creditcardmanager.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.creditcardmanager.data.DataOrException
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.repository.CreditCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val cardRepository: CreditCardRepository) :
    ViewModel() {

    private val _creditCards =
        MutableStateFlow<DataOrException<List<CreditCard>, Boolean, Exception>>(
            DataOrException(
                listOf(),
                true,
                Exception("")
            )
        )
    val creditCards = _creditCards.asStateFlow()


    init {
        viewModelScope.launch {
            try {
                cardRepository.getAllCreditCards().distinctUntilChanged().collect { cardList ->
                    _creditCards.value = _creditCards.value.copy(
                        data = cardList,
                        loading = false,
                        Exception("")
                    )
                }
            } catch (e: Exception) {
                _creditCards.value.exception = e
                _creditCards.value.loading = true


            }

        }

    }

    fun updateCreditCard(creditCard: CreditCard) =
        viewModelScope.launch { cardRepository.updateCreditCard(creditCard) }


    fun deleteCreditCard(creditCard: CreditCard) =
        viewModelScope.launch { cardRepository.deleteCreditCard(creditCard) }

    fun deleteAllCreditCard() =
        viewModelScope.launch { cardRepository.deleteAllCreditCards() }
}