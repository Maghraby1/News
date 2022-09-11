package com.maghraby.news.di.module

import com.maghraby.news.data.repository.MainRepository
import org.koin.dsl.module

val repoModule = module {
    single {
        MainRepository(get())
    }
}