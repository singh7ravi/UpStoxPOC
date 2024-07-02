package com.example.upstoxpoc.domain.utils

import com.example.upstoxpoc.data.model.CryptoItem

data class ViewState(
    val filterList: List<CryptoItem> = emptyList()
)
