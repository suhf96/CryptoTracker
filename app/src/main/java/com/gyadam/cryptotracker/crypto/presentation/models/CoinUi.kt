package com.gyadam.cryptotracker.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.gyadam.cryptotracker.core.presentation.util.getDrawableIdForCoin
import com.gyadam.cryptotracker.crypto.domain.Coin
import com.gyadam.cryptotracker.crypto.presentation.coin_detail.DataPoint
import com.gyadam.cryptotracker.crypto.presentation.coin_list.components.toDisplayableNumber

data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    val coinPriceHistory: List<DataPoint> = emptyList(),
    @DrawableRes val iconRes: Int
)

data class DisplayableNumber(
    val value: Double,
    val formatted: String
)

fun Coin.toCoinUi(): CoinUi {
    return CoinUi(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        priceUsd = priceUsd.toDisplayableNumber(),
        marketCapUsd = marketCapUsd.toDisplayableNumber(),
        changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
        iconRes = getDrawableIdForCoin(symbol)
    )
}
