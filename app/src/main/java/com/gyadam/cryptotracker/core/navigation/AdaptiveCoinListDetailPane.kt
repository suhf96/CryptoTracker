package com.gyadam.cryptotracker.core.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gyadam.cryptotracker.core.presentation.util.ObserveAsEvents
import com.gyadam.cryptotracker.core.presentation.util.toString
import com.gyadam.cryptotracker.crypto.presentation.coin_detail.CoinDetailScreen
import com.gyadam.cryptotracker.crypto.presentation.coin_list.CoinListAction
import com.gyadam.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.gyadam.cryptotracker.crypto.presentation.coin_list.CoinListScreen
import com.gyadam.cryptotracker.crypto.presentation.coin_list.CoinListViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveCoinListDetailPane(
    coinListViewModel: CoinListViewModel,
    modifier: Modifier = Modifier
) {
    val state by coinListViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(events = coinListViewModel.events) { event ->
        when (event) {
            is CoinListAction.Error -> Toast.makeText(
                context,
                event.error.toString(context),
                Toast.LENGTH_LONG
            ).show()
        }
    }
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoinListScreen(
                    modifier = modifier,
                    onEvent = { event ->
                        coinListViewModel.onEvent(event)
                        when (event) {
                            is CoinListEvent.OnCoinClick -> {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail
                                )
                            }

                            CoinListEvent.OnRefresh -> TODO()
                        }
                    },
                    state = state
                )
            }

        },
        detailPane = {
            AnimatedPane {
                CoinDetailScreen(
                    modifier = modifier,
                    state = state
                )
            }
        }
    )
}