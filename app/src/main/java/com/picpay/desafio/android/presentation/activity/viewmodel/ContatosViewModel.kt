package com.picpay.desafio.android.presentation.activity.viewmodel

import androidx.lifecycle.*
import com.picpay.desafio.android.data.db.dao.PicpayUserDao
import com.picpay.desafio.android.presentation.viewstates.OnLoadingState
import com.picpay.desafio.android.presentation.viewstates.OnPicPayServiceResponse
import com.picpay.desafio.android.data.repository.ContatosRepository
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.usecase.ContatosRepositoryUseCase
import kotlinx.coroutines.launch

class ContatosViewModel(
    repository: ContatosRepository,
    picpayUserDao: PicpayUserDao
) : ViewModel() {

    private val usecase = ContatosRepositoryUseCase(picpayUserDao, repository)

    private val picPayServiceResponse: MutableLiveData<OnPicPayServiceResponse> = MutableLiveData()
    val onPicPayServiceResponse: LiveData<OnPicPayServiceResponse> = picPayServiceResponse

    private val loadingState: MutableLiveData<OnLoadingState> = MutableLiveData()
    val onLoadingState: LiveData<OnLoadingState> = loadingState

    fun fetchPicPayUsers() {
        viewModelScope.launch {
            loadingState.postValue(OnLoadingState.Show)
            val isResponseSuccessful = usecase.create()
            setPicPayUsersState(isResponseSuccessful)
        }.invokeOnCompletion {
            loadingState.postValue(OnLoadingState.Hide)
        }
    }

    private fun setPicPayUsersState(isResponseSuccessful: Boolean) {
        viewModelScope.launch {
            if (isResponseSuccessful) {
               validateCachedData(usecase.read())
            } else {
                picPayServiceResponse.postValue(OnPicPayServiceResponse.OnFailure(usecase.read()))
            }
        }
    }

    private fun validateCachedData(cache: List<User>) {
        if(!cache.isNullOrEmpty()) {
            picPayServiceResponse.postValue(OnPicPayServiceResponse.OnSuccess(cache))
        } else {
            picPayServiceResponse.postValue(OnPicPayServiceResponse.OnFailure(cache))
        }
    }

    class ContatosViewModelFactory(
        private val repository: ContatosRepository,
        private val picpayUserDao: PicpayUserDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ContatosViewModel(repository, picpayUserDao) as T
        }
    }
}