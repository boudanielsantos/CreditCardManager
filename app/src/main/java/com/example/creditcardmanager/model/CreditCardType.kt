package com.example.creditcardmanager.model

import com.example.creditcardmanager.R

enum class CreditCardType {
    VISA(),
    MASTER_CARD,
    AMERICAN_EXPRESS,
    JCB,
    DISCOVER_CARD,
    DINERS_CLUB_INTERNATIONAL,
    OTHERS;

    companion object {
        val cardTypeNames = listOf(
            "VISA",
            "MASTERCARD",
            "AMERICAN EXPRESS",
            "JCB",
            "DISCOVER CARD",
            "DINERS CLUB INTERNATIONAL",
            "OTHERS"
        )

        fun getCardType(cardType: String): CreditCardType =
            when (cardType) {
                "VISA" -> VISA
                "MASTERCARD" -> MASTER_CARD
                "AMERICAN EXPRESS" -> AMERICAN_EXPRESS
                "JCB" -> JCB
                "DISCOVER CARD" -> DISCOVER_CARD
                "DINERS CLUB INTERNATIONAL" -> DINERS_CLUB_INTERNATIONAL
                else -> OTHERS
            }

        fun getCardTypeIcon(cardType: CreditCardType): Int =
            when (cardType) {
                VISA -> R.drawable.visa
                JCB -> R.drawable.jcb
                MASTER_CARD -> R.drawable.mastercard
                DISCOVER_CARD -> R.drawable.discover
                DINERS_CLUB_INTERNATIONAL -> R.drawable.diners_club
                AMERICAN_EXPRESS -> R.drawable.amex
                else -> R.drawable.others

            }
    }


}
