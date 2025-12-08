package com.example.creditcardmanager.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.creditcardmanager.data.DataOrException
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.repository.CreditCardRepository
import com.example.creditcardmanager.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.Calendar
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
                _creditCards.value.loading = true
                cardRepository.getAllCreditCards().distinctUntilChanged()
                    .collect { cardListFromDb ->
                        if (cardListFromDb.isNotEmpty()) {
                            // Process the list to check for cycle updates
                            val processedList = cardListFromDb.map { card ->
                                val today = Calendar.getInstance()
                                val statementMonthCalendar = Calendar.getInstance()
                                if (today.get(Calendar.DAY_OF_MONTH) < card.statementDay) {
                                    statementMonthCalendar.add(Calendar.MONTH, -1)
                                }


                                val dueDateMonthCalendar = Calendar.getInstance()
                                if (today.get(Calendar.DAY_OF_MONTH) > card.dueDay) {
                                    dueDateMonthCalendar.add(Calendar.MONTH, 1)
                                }

                                val updatedCard = Utils.updateCreditCardCycle(
                                    card,
                                    statementMonthCalendar,
                                    dueDateMonthCalendar
                                )

                                //If a new cycle is started update the card
                                if (updatedCard !== card) {
                                    updateCreditCard(updatedCard)
                                }
                                updatedCard
                            }
                            _creditCards.value = _creditCards.value.copy(
                                data = processedList,
                                loading = false,
                                exception = null
                            )

                        } else {
                            _creditCards.value = _creditCards.value.copy(
                                data = emptyList(),
                                loading = false
                            )
                        }
                    }
            } catch (e: Exception) {
                _creditCards.value = _creditCards.value.copy(loading = false, exception = e)
            }
        }
    }

    fun updateCreditCard(creditCard: CreditCard) =
        viewModelScope.launch { cardRepository.updateCreditCard(creditCard) }


    fun deleteCreditCard(creditCard: CreditCard) =
        viewModelScope.launch { cardRepository.deleteCreditCard(creditCard) }

    fun deleteAllCreditCard() =
        viewModelScope.launch {
            cardRepository.deleteAllCreditCards()
        }


}

