package com.gyadam.cryptotracker.crypto.presentation.coin_list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gyadam.cryptotracker.R
import com.gyadam.cryptotracker.core.presentation.util.toString
import com.gyadam.cryptotracker.crypto.presentation.coin_list.components.BottomBar
import com.gyadam.cryptotracker.crypto.presentation.coin_list.components.CoinListItem
import com.gyadam.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.gyadam.cryptotracker.ui.theme.CryptoTrackerTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.withContext

@Composable
fun CoinListScreen(
    state: CoinListState,
    hazeState: HazeState,
    paddingValues: PaddingValues,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier
) {

    if (state.isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            LazyColumn(
                modifier = modifier
                    .haze(
                        state = hazeState,
                        style = HazeStyle(
                            blurRadius = 13.dp,
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        )
                    ),
                contentPadding = PaddingValues(
                    top = paddingValues.calculateTopPadding() + 22.dp,
                    bottom = 90.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.coins) { coinUi ->
                    CoinListItem(
                        coinUi = coinUi,
                        onClick = { onAction(CoinListAction.OnCoinClick(coinUi)) },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    HorizontalDivider()
                }
            }
            BottomBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp)
                    .align(Alignment.BottomStart)
                    .padding(bottom = 26.dp)
                    .hazeChild(
                        state = hazeState,
                        shape = RoundedCornerShape(26.dp)
                    )
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun CoinListScreenPreview() {
    CryptoTrackerTheme {
        CoinListScreen(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            hazeState = HazeState(),
            onAction = {},
            paddingValues = PaddingValues(),
            state = CoinListState(isLoading = false, coins = (0..100).map { previewCoin })
        )
    }
}