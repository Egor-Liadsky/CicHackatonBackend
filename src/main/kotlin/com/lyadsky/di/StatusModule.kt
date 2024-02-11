package com.lyadsky.di

import com.lyadsky.database.dao.status.StatusDAO
import com.lyadsky.database.dao.status.StatusDAOImpl
import org.koin.dsl.module

val statusModule = module {
    single<StatusDAO> { StatusDAOImpl() }
}