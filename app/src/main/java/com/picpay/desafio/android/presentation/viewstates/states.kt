package com.picpay.desafio.android.presentation.viewstates

import com.picpay.desafio.android.domain.model.User

sealed class OnPicPayServiceResponse {
    data class OnServiceResponse(val users: List<User>?): OnPicPayServiceResponse()
    object OnCacheUnavailable: OnPicPayServiceResponse()
}

sealed class OnLoadingState {
    object Show: OnLoadingState()
    object Hide: OnLoadingState()
}
