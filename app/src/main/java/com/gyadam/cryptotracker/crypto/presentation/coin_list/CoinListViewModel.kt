package com.gyadam.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyadam.cryptotracker.core.domain.util.onError
import com.gyadam.cryptotracker.core.domain.util.onSuccess
import com.gyadam.cryptotracker.crypto.domain.CoinDataSource
import com.gyadam.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {

    private val state = MutableStateFlow(CoinListState())
    val uiState: StateFlow<CoinListState> = state
        .onStart { loadCoins() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            CoinListState()
        )
    private val _events = Channel<CoinListAction>()
    val events = _events.receiveAsFlow()

    fun onEvent(event: CoinListEvent) {
        when (event) {
            is CoinListEvent.OnCoinClick -> {
                //TODO
            }

            CoinListEvent.OnRefresh -> loadCoins()
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            state.update {
                it.copy(
                    isLoading = true
                )
            }
            coinDataSource
                .getCoins()
                .onSuccess { coins ->
                    state.update {
                        it.copy(isLoading = false, coins = coins.map { coin -> coin.toCoinUi() })
                    }
                }
                .onError { error ->
                    state.update {
                        it.copy(isLoading = false)
                    }
                    _events.send(CoinListAction.Error(error))
                }
        }
    }
}