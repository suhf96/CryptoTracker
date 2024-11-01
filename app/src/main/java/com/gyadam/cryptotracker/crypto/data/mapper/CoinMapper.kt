package com.gyadam.cryptotracker.crypto.data.mapper

import com.gyadam.cryptotracker.crypto.data.networking.dto.CoinDTO
import com.gyadam.cryptotracker.crypto.domain.Coin

fun CoinDTO.toCoin(): Coin {
    return Coin(
        id, rank, name, symbol, marketCapUsd, priceUsd, changePercent24Hr
    )
}