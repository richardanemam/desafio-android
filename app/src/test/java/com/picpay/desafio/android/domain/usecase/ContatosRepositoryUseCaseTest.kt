package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.common.BaseTest
import com.picpay.desafio.android.data.api.PicPayService
import com.picpay.desafio.android.data.db.PicpayUserDatabase
import com.picpay.desafio.android.data.db.dao.PicpayUserDao
import com.picpay.desafio.android.data.repository.ContatosRepository
import com.picpay.desafio.android.presentation.activity.viewmodel.ContatosViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ContatosRepositoryUseCaseTest : BaseTest() {

    private val useCase: ContatosRepositoryUseCase = mockk()
    private lateinit var viewModel: ContatosViewModel
    private val dao = mockk<PicpayUserDao>()
    private val db = mockk<PicpayUserDatabase>()
    private val repository = mockk<ContatosRepository>()
    private val service = mockk<PicPayService>()

    @Before
    fun setUp() {
        every { db.picpayUserDao() } returns dao
        viewModel = ContatosViewModel(repository, dao)
    }

    @Test
    fun `verify caching data`() {
        runBlocking {
            coEvery { service.getUsers() } returns getResponse()
            coEvery { repository.getUsers() } returns getResponse()
            coEvery { dao.insertAll(getUserEntity()[0]) } returns Unit
            coEvery { dao.getAll() } returns getUserEntity()
            coEvery { useCase.create() } returns Unit

            viewModel.fetchPicPayUsers()
            useCase.create()
            io.mockk.verify { runBlocking {  useCase.create() }}
        }
    }
}