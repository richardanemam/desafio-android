package com.picpay.desafio.android.di

import androidx.room.Room
import com.picpay.desafio.android.data.db.PicpayUserDatabase
import com.picpay.desafio.android.data.repository.ContatosRepository
import com.picpay.desafio.android.domain.usecase.ContatosRepositoryUseCase
import com.picpay.desafio.android.presentation.activity.viewmodel.ContatosViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    single {
        Room.databaseBuilder(androidApplication(),  PicpayUserDatabase::class.java, "picpay_user_database").build()
    }

    single {
        get<PicpayUserDatabase>().picpayUserDao()
    }

    single {
        ContatosRepository()
    }

    single {
        ContatosRepositoryUseCase(picpayUserDao = get(), repository = get())
    }

    viewModel {
        ContatosViewModel(
            repository = get(),
            picpayUserDao = get()
        )
    }
}