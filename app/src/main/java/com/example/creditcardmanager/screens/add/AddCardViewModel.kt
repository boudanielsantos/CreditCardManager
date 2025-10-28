package com.example.creditcardmanager.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.creditcardmanager.model.CreditCard
import com.example.creditcardmanager.repository.CreditCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(private val cardRepository: CreditCardRepository) :
    ViewModel() {

    fun addCard(creditCard: CreditCard) =
        viewModelScope.launch {
            cardRepository.insertCreditCard(creditCard)
        }


}