package com.example.upstoxpoc
import com.example.upstoxpoc.data.model.CryptoItem
import com.example.upstoxpoc.domain.utils.COIN
import com.example.upstoxpoc.domain.utils.FilterType
import com.example.upstoxpoc.domain.utils.TOKEN
import com.example.upstoxpoc.domain.utils.Utils.Companion.convertToFilterTypeList
import com.example.upstoxpoc.domain.utils.Utils.Companion.getCrytoTypeIcon
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {

    // Mock constants for testing
    private val FILTER_ACTIVE_COIN = "Active Coins"
    private val FILTER_INACTIVE_COINS = "Inactive Coins"
    private val FILTER_ONLY_TOKEN = "Only Tokens"
    private val FILTER_ONLY_COINS = "Only Coins"
    private val FILTER_NEW_COINS = "New Coins"

    @Test
    fun testGetCrytoTypeIcon_activeCoin() {
        val cryptoItem = CryptoItem(name = "Bitcoin", symbol = "BTC", isNew = false, isActive = true, type = COIN)
        val icon = getCrytoTypeIcon(cryptoItem)
        assertEquals(R.drawable.active_coin, icon)
    }

    @Test
    fun testGetCrytoTypeIcon_inactiveCoin() {
        val cryptoItem = CryptoItem(name = "Ripple", symbol = "XRP", isNew = false, isActive = false, type = COIN)
        val icon = getCrytoTypeIcon(cryptoItem)
        assertEquals(R.drawable.inactive_image, icon)
    }

    @Test
    fun testGetCrytoTypeIcon_activeToken() {
        val cryptoItem = CryptoItem(name = "Ethereum", symbol = "ETH", isNew = false, isActive = true, type = TOKEN)
        val icon = getCrytoTypeIcon(cryptoItem)
        assertEquals(R.drawable.token_active, icon)
    }

    @Test
    fun testGetCrytoTypeIcon_defaultIcon() {
        val cryptoItem = CryptoItem(name = "Unknown", symbol = "UNK", isNew = false, isActive = false, type = "unknown")
        val icon = getCrytoTypeIcon(cryptoItem)
        assertEquals(R.drawable.ic_launcher_foreground, icon)
    }

    @Test
    fun testConvertToFilterTypeList_noFilters() {
        val filters = emptyList<String>()
        val result = convertToFilterTypeList(filters)
        assertEquals(emptyList<FilterType>(), result)
    }

    @Test
    fun testConvertToFilterTypeList_activeCoin() {
        val filters = listOf(FILTER_ACTIVE_COIN)
        val result = convertToFilterTypeList(filters)
        assertEquals(listOf(FilterType.ACTIVE_COINS), result)
    }

    @Test
    fun testConvertToFilterTypeList_inactiveCoins() {
        val filters = listOf(FILTER_INACTIVE_COINS)
        val result = convertToFilterTypeList(filters)
        assertEquals(listOf(FilterType.INACTIVE_COINS), result)
    }

    @Test
    fun testConvertToFilterTypeList_onlyTokens() {
        val filters = listOf(FILTER_ONLY_TOKEN)
        val result = convertToFilterTypeList(filters)
        assertEquals(listOf(FilterType.ONLY_TOKENS), result)
    }

    @Test
    fun testConvertToFilterTypeList_onlyCoins() {
        val filters = listOf(FILTER_ONLY_COINS)
        val result = convertToFilterTypeList(filters)
        assertEquals(listOf(FilterType.ONLY_COINS), result)
    }

    @Test
    fun testConvertToFilterTypeList_newCoinsActive() {
        val filters = listOf(FILTER_NEW_COINS, FILTER_ACTIVE_COIN)
        val result = convertToFilterTypeList(filters)
        assertEquals(listOf(FilterType.NEW_COINS_ACTIVE), result)
    }

    @Test
    fun testConvertToFilterTypeList_newCoinsInactive() {
        val filters = listOf(FILTER_NEW_COINS, FILTER_INACTIVE_COINS)
        val result = convertToFilterTypeList(filters)
        assertEquals(listOf(FilterType.NEW_COINS_INACTIVE), result)
    }

    @Test
    fun testConvertToFilterTypeList_multipleFilters() {
        val filters = listOf(FILTER_ACTIVE_COIN, FILTER_ONLY_TOKEN)
        val result = convertToFilterTypeList(filters)
        assertEquals(listOf(FilterType.ACTIVE_COINS, FilterType.ONLY_TOKENS), result)
    }
}
