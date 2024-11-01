package com.gyadam.cryptotracker.di

import com.gyadam.cryptotracker.core.data.networking.HttpClientFactory
import com.gyadam.cryptotracker.crypto.data.networking.RemoteCoinDataSource
import com.gyadam.cryptotracker.crypto.domain.CoinDataSource
import com.gyadam.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appmodule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)
}