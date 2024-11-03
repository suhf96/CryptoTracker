package com.gyadam.cryptotracker.crypto.presentation.coin_list

import com.gyadam.cryptotracker.core.domain.util.NetworkError


sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}