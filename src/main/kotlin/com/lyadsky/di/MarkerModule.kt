package com.lyadsky.di

import com.lyadsky.database.dao.marker.MarkerDAO
import com.lyadsky.database.dao.marker.MarkerDAOImpl
import com.lyadsky.features.marker.MarkerController
import org.koin.dsl.module

val markerModule = module {
    single<MarkerDAO> { MarkerDAOImpl() }
    single { MarkerController(get()) }
}