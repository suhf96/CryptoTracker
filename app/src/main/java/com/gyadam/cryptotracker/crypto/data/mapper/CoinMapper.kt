package com.gyadam.cryptotracker.crypto.data.mapper

import com.gyadam.cryptotracker.crypto.data.networking.dto.CoinDTO
import com.gyadam.cryptotracker.crypto.data.networking.dto.CoinPriceDTO
import com.gyadam.cryptotracker.crypto.domain.Coin
import com.gyadam.cryptotracker.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinDTO.toCoin(): Coin {
    return Coin(
        id, rank, name, symbol, marketCapUsd, priceUsd, changePercent24Hr
    )
}


fun CoinPriceDTO.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant.ofEpochMilli(time)
            .atZone(ZoneId.of("UTC"))
    )
}