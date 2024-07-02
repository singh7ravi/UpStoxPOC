package com.example.upstoxpoc;

import com.example.upstoxpoc.data.model.CryptoItem
import com.example.upstoxpoc.domain.utils.Converter
import com.example.upstoxpoc.domain.utils.FilterType
import org.junit.Assert.assertEquals
import org.junit.Test

class ConverterTest {

    private val cryptoItems = listOf(
            CryptoItem("Bitcoin", "BTC", false, true, "coin"),
            CryptoItem("Ethereum", "ETH", false, false, "token"),
            CryptoItem("Ripple", "XRP", false, false, "coin"),
            CryptoItem("Cardano", "ADA", true, true, "coin"),
            CryptoItem("Chainlink", "LINK", true, true, "token"),
            CryptoItem("Polkadot", "DOT", true, false, "coin")
    )

    @Test
    fun testFilterCryptoItems_activeCoins() {
        val filterTypes = listOf(FilterType.ACTIVE_COINS)
        val result = Converter.filterCryptoItems(cryptoItems, filterTypes)
        val expected = listOf(
                CryptoItem("Bitcoin", "BTC", false, true, "coin")
        )
        assertEquals(expected, result)
    }

    @Test
    fun testFilterCryptoItems_inactiveCoins() {
        val filterTypes = listOf(FilterType.INACTIVE_COINS)
        val result = Converter.filterCryptoItems(cryptoItems, filterTypes)
        val expected = listOf(
                CryptoItem("Ripple", "XRP", false, false, "coin")
        )
        assertEquals(expected, result)
    }

    @Test
    fun testFilterCryptoItems_onlyTokens() {
        val filterTypes = listOf(FilterType.ONLY_TOKENS)
        val result = Converter.filterCryptoItems(cryptoItems, filterTypes)
        val expected = listOf(
                CryptoItem("Ethereum", "ETH", false, false, "token"),
                CryptoItem("Chainlink", "LINK", true, true, "token")
        )
        assertEquals(expected, result)
    }

    @Test
    fun testFilterCryptoItems_onlyCoins() {
        val filterTypes = listOf(FilterType.ONLY_COINS)
        val result = Converter.filterCryptoItems(cryptoItems, filterTypes)
        val expected = listOf(
                CryptoItem("Bitcoin", "BTC", false, true, "coin"),
                CryptoItem("Ripple", "XRP", false, false, "coin")
        )
        assertEquals(expected, result)
    }

    @Test
    fun testFilterCryptoItems_newCoins() {
        val filterTypes = listOf(FilterType.NEW_COINS)
        val result = Converter.filterCryptoItems(cryptoItems, filterTypes)
        val expected = listOf(
                CryptoItem("Cardano", "ADA", true, true, "coin"),
                CryptoItem("Polkadot", "DOT", true, false, "coin")
        )
        assertEquals(expected, result)
    }

    @Test
    fun testFilterCryptoItems_newCoinsActive() {
        val filterTypes = listOf(FilterType.NEW_COINS_ACTIVE)
        val result = Converter.filterCryptoItems(cryptoItems, filterTypes)
        val expected = listOf(
                CryptoItem("Cardano", "ADA", true, true, "coin")
        )
        assertEquals(expected, result)
    }

    @Test
    fun testFilterCryptoItems_newCoinsInactive() {
        val filterTypes = listOf(FilterType.NEW_COINS_INACTIVE)
        val result = Converter.filterCryptoItems(cryptoItems, filterTypes)
        val expected = listOf(
                CryptoItem("Polkadot", "DOT", true, false, "coin")
        )
        assertEquals(expected, result)
    }

    // TestCases for filterCryptoItemsByNamePrefix
    @Test
    fun testFilterCryptoItemsByNamePrefix_b() {
        val result = Converter.filterCryptoItemsByNamePrefix(cryptoItems, "B")
        val expected = listOf(
            CryptoItem("Bitcoin", "BTC", false, true, "coin")
        )
        assertEquals(expected, result)
    }

    @Test
    fun testFilterCryptoItemsByNamePrefix_e() {
        val result = Converter.filterCryptoItemsByNamePrefix(cryptoItems, "E")
        val expected = listOf(
            CryptoItem("Ethereum", "ETH", false, false, "token")
        )
        assertEquals(expected, result)
    }

    @Test
    fun testFilterCryptoItemsByNamePrefix_x() {
        val result = Converter.filterCryptoItemsByNamePrefix(cryptoItems, "X")
        val expected = listOf(
            CryptoItem("Ripple", "XRP", false, false, "coin")
        )
        assertEquals(expected, result)
    }

    @Test
    fun testFilterCryptoItemsByNamePrefix_a() {
        val result = Converter.filterCryptoItemsByNamePrefix(cryptoItems, "A")
        val expected = listOf(
            CryptoItem("Cardano", "ADA", true, true, "coin")
        )
        assertEquals(expected, result)
    }

    @Test
    fun testFilterCryptoItemsByNamePrefix_l() {
        val result = Converter.filterCryptoItemsByNamePrefix(cryptoItems, "L")
        val expected = listOf(
            CryptoItem("Chainlink", "LINK", true, true, "token")
        )
        assertEquals(expected, result)
    }

    @Test
    fun testFilterCryptoItemsByNamePrefix_d() {
        val result = Converter.filterCryptoItemsByNamePrefix(cryptoItems, "D")
        val expected = listOf(
            CryptoItem("Polkadot", "DOT", true, false, "coin")
        )
        assertEquals(expected, result)
    }

    @Test
    fun testFilterCryptoItemsByNamePrefix_empty() {
        val result = Converter.filterCryptoItemsByNamePrefix(cryptoItems, "")
        val expected = cryptoItems
        assertEquals(expected, result)
    }

    @Test
    fun testFilterCryptoItemsByNamePrefix_noMatch() {
        val result = Converter.filterCryptoItemsByNamePrefix(cryptoItems, "Z")
        val expected = emptyList<CryptoItem>()
        assertEquals(expected, result)
    }
}
