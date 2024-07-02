package com.example.upstoxpoc.domain.utils

import com.example.upstoxpoc.data.model.CryptoItem

object Converter {

    fun filterCryptoItems(cryptoItems: List<CryptoItem>, filterTypes: List<FilterType>): List<CryptoItem> {
        return cryptoItems.filter { item ->
            filterTypes.any { filterType ->
                when (filterType) {
                    FilterType.ACTIVE_COINS -> !item.isNew && item.isActive && item.type == COIN
                    FilterType.INACTIVE_COINS -> !item.isNew && !item.isActive && item.type == COIN
                    FilterType.ONLY_TOKENS -> item.type == TOKEN
                    FilterType.ONLY_COINS -> !item.isNew && item.type == COIN
                    FilterType.NEW_COINS -> item.isNew && item.type == COIN
                    FilterType.NEW_COINS_ACTIVE -> item.isNew && item.isActive && item.type == COIN
                    FilterType.NEW_COINS_INACTIVE -> item.isNew && !item.isActive && item.type == COIN

                }
            }
        }
    }
    fun filterCryptoItemsByNamePrefix(cryptoItems: List<CryptoItem>, prefix: String): List<CryptoItem> {
        return cryptoItems.filter { it.name.startsWith(prefix, ignoreCase = true)
                || it.symbol.startsWith(prefix, ignoreCase = true)}
    }
}