package com.example.upstoxpoc.domain.utils

import com.example.upstoxpoc.R
import com.example.upstoxpoc.data.model.CryptoItem

class Utils {

    companion object {

        fun getCrytoTypeIcon(cryptoItem: CryptoItem): Int {
            return when {
                cryptoItem.isActive && cryptoItem.type == COIN -> R.drawable.active_coin
                !cryptoItem.isActive && cryptoItem.type == COIN -> R.drawable.inactive_image
                cryptoItem.type == TOKEN -> R.drawable.token_active
                else -> R.drawable.ic_launcher_foreground // Default icon if none of the conditions match
            }
        }
        fun convertToFilterTypeList(filters: List<String>): List<FilterType> {
            val filterSet = filters.map { it.trim() }.toSet()

            // Handle special cases for "New Coins"
            val hasNewCoins = FILTER_NEW_COINS in filterSet
            val hasActiveCoins = FILTER_ACTIVE_COIN in filterSet
            val hasInactiveCoins = FILTER_INACTIVE_COINS in filterSet

            val specialCaseType: FilterType? = when {
                hasNewCoins && hasActiveCoins -> FilterType.NEW_COINS_ACTIVE
                hasNewCoins && hasInactiveCoins -> FilterType.NEW_COINS_INACTIVE
                hasNewCoins -> FilterType.NEW_COINS
                else -> null
            }

            val normalFilterTypes = filters.mapNotNull { filter ->
                when (filter.trim()) {
                    FILTER_ACTIVE_COIN -> if (specialCaseType == FilterType.NEW_COINS_ACTIVE) null else FilterType.ACTIVE_COINS
                    FILTER_INACTIVE_COINS -> if (specialCaseType == FilterType.NEW_COINS_INACTIVE) null else FilterType.INACTIVE_COINS
                    FILTER_ONLY_TOKEN -> FilterType.ONLY_TOKENS
                    FILTER_ONLY_COINS -> FilterType.ONLY_COINS
                    FILTER_NEW_COINS -> null
                    else -> null
                }
            }.toMutableList()

            // Add special case type if it's not null
            specialCaseType?.let {
                normalFilterTypes.add(it)
            }

            return normalFilterTypes
        }
    }
}