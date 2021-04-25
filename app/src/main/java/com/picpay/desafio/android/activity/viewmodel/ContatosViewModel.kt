package com.picpay.desafio.android.activity.viewmodel

import androidx.lifecycle.*
import com.picpay.desafio.android.states.OnLoadingState
import com.picpay.desafio.android.states.OnPicPayServiceResponse
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.repository.ContatosRespository
import kotlinx.coroutines.launch
import retrofit2.Response

class ContatosViewModel(private val repository: ContatosRespository): ViewModel() {

    private val picPayServiceResponse: MutableLiveData<OnPicPayServiceResponse> = MutableLiveData()
    val onPicPayServiceResponse: LiveData<OnPicPayServiceResponse> = picPayServiceResponse

    private val loadingState: MutableLiveData<OnLoadingState> = MutableLiveData()
    val onLoadingState: LiveData<OnLoadingState> = loadingState

    fun fetchPicPayUsers() {
       viewModelScope.launch {
           loadingState.postValue(OnLoadingState.Show)
           val response = repository.getUsers()
           setPicPayUsersState(response)
       }.invokeOnCompletion {
           loadingState.postValue(OnLoadingState.Hide)
       }
    }

    private fun setPicPayUsersState(response: Response<List<User>>) {
        if(response.isSuccessful) {
            picPayServiceResponse.postValue(OnPicPayServiceResponse.OnSuccess(response.body()))
        } else {
            picPayServiceResponse.postValue(OnPicPayServiceResponse.OnFailure)
        }
    }

    class ContatosViewModelFactory(private val repository: ContatosRespository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ContatosViewModel(repository) as T
        }
    }
}