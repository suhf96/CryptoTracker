package com.gyadam.cryptotracker.crypto.data.networking

import com.gyadam.cryptotracker.core.data.networking.constructUrl
import com.gyadam.cryptotracker.core.data.networking.safeCall
import com.gyadam.cryptotracker.core.domain.util.NetworkError
import com.gyadam.cryptotracker.core.domain.util.Result
import com.gyadam.cryptotracker.core.domain.util.map
import com.gyadam.cryptotracker.crypto.data.mapper.toCoin
import com.gyadam.cryptotracker.crypto.data.networking.dto.CoinsResponseDTO
import com.gyadam.cryptotracker.crypto.domain.Coin
import com.gyadam.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {

    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDTO> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { reponse ->
            reponse.data.map { it.toCoin() }
        }
    }
}