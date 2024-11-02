package com.gyadam.cryptotracker.crypto.data.networking

import com.gyadam.cryptotracker.core.data.networking.constructUrl
import com.gyadam.cryptotracker.core.data.networking.safeCall
import com.gyadam.cryptotracker.core.domain.util.NetworkError
import com.gyadam.cryptotracker.core.domain.util.Result
import com.gyadam.cryptotracker.core.domain.util.map
import com.gyadam.cryptotracker.crypto.data.mapper.toCoin
import com.gyadam.cryptotracker.crypto.data.mapper.toCoinPrice
import com.gyadam.cryptotracker.crypto.data.networking.dto.CoinHistoryDTO
import com.gyadam.cryptotracker.crypto.data.networking.dto.CoinsResponseDTO
import com.gyadam.cryptotracker.crypto.domain.Coin
import com.gyadam.cryptotracker.crypto.domain.CoinDataSource
import com.gyadam.cryptotracker.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

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

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start.withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        val endMillis = end.withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        return safeCall<CoinHistoryDTO> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map {
                it.toCoinPrice()
            }
        }
    }
}