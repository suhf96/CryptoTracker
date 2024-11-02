package com.gyadam.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryDTO(
    val data: List<CoinPriceDTO>
)
