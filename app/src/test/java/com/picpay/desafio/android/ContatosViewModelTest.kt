package com.picpay.desafio.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.data.api.PicPayService
import com.picpay.desafio.android.data.db.PicpayUserDatabase
import com.picpay.desafio.android.data.db.dao.PicpayUserDao
import com.picpay.desafio.android.data.repository.ContatosRepository
import com.picpay.desafio.android.domain.usecase.ContatosRepositoryUseCase
import com.picpay.desafio.android.presentation.activity.viewmodel.ContatosViewModel
import com.picpay.desafio.android.presentation.viewstates.OnPicPayServiceResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.*
import org.junit.*

class ContatosViewModelTest : BaseTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: ContatosViewModel
    private val dao = mockk<PicpayUserDao>()
    private val db = mockk<PicpayUserDatabase>()
    private val repository = mockk<ContatosRepository>()
    private val api = mockk<PicPayService>()
    private val useCase = mockk<ContatosRepositoryUseCase>()

    @Before
    fun setUp() {
        every { db.picpayUserDao() } returns dao
        viewModel = ContatosViewModel(repository, dao)
    }

    @Test
    fun `when fetching picpay users it should set service response state`() {
        runBlocking {
            coEvery { useCase.create() } returns Unit
            coEvery { repository.getUsers() } returns getResponse()
            coEvery { api.getUsers() } returns getResponse()
            coEvery { useCase.read() } returns getUsers()
            coEvery { dao.insertAll(getUserEntity()[0]) } returns Unit
            coEvery { dao.getAll() } returns getUserEntity()
        }

        viewModel.fetchPicPayUsers()
        Assert.assertEquals(OnPicPayServiceResponse.OnServiceResponse(getUsers()),
            viewModel.onPicPayServiceResponse.value)
    }
}