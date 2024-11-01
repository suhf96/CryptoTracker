package com.gyadam.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinDTO(
    val id:String,
    val rank:Int,
    val name:String,
    val symbol :String,
    val marketCapUsd:Double,
    val priceUsd: Double,
    val changePercent24Hr:Double
)
