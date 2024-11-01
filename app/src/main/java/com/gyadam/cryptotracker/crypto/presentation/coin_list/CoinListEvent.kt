package com.gyadam.cryptotracker.crypto.presentation.coin_list

import com.gyadam.cryptotracker.crypto.presentation.models.CoinUi

sealed interface CoinListEvent {
    data class OnCoinClick(val coinUi: CoinUi) : CoinListEvent
    data object OnRefresh : CoinListEvent
}