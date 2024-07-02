package com.example.upstoxpoc.data.model

import com.google.gson.annotations.SerializedName

data class CryptoItem(
    val name: String,
    val symbol: String,
    @SerializedName("is_new") val isNew: Boolean,
    @SerializedName("is_active") val isActive: Boolean,

    val type: String
)
