package com.example.upstoxpoc.domain.utils

import com.example.upstoxpoc.data.model.FilterCategory

const val COIN = "coin"
const val TOKEN = "token"

const val FILTER_ACTIVE_COIN = "Active Coins"
const val FILTER_INACTIVE_COINS = "Inactive Coins"
const val FILTER_ONLY_TOKEN = "Only Tokens"
const val FILTER_ONLY_COINS = "Only Coins"
const val FILTER_NEW_COINS = "New Coins"
val filterCategories = listOf(
    FilterCategory(FILTER_ACTIVE_COIN, false),
    FilterCategory(FILTER_INACTIVE_COINS, false),
    FilterCategory(FILTER_ONLY_TOKEN, false),
    FilterCategory(FILTER_ONLY_COINS, false),
    FilterCategory(FILTER_NEW_COINS, false)
)
enum class FilterType {
    ACTIVE_COINS,
    INACTIVE_COINS,
    ONLY_TOKENS,
    ONLY_COINS,
    NEW_COINS,
    NEW_COINS_ACTIVE,
    NEW_COINS_INACTIVE,
}

