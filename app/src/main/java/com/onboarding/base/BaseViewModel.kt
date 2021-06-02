package com.onboarding.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.onboarding.util.NO_INTERNET
import java.net.UnknownHostException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {
    fun <T : Any> call(deferred: Deferred<Response<T>>, apiCallbackListener: ApiCallbackListener<T>) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val response = deferred.await()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        apiCallbackListener.onSuccess(response.body())
                    } else {
                        apiCallbackListener.onError(Throwable(response.message()))
                    }
                }
            }.onFailure {
                when (it) {
                    is HttpException -> {
                        withContext(Dispatchers.Main) {
                            apiCallbackListener.onError(it)
                        }
                    }
                    is JsonParseException -> {
                        withContext(Dispatchers.Main) {
                            apiCallbackListener.onError(it)
                        }
                    }
                    is UnknownHostException -> {
                        withContext(Dispatchers.Main) {
                            var throwable = Throwable(NO_INTERNET, it.cause)
                            apiCallbackListener.onError(throwable)
                        }
                    }

                }
            }
        }
    }

    interface ApiCallbackListener<T> {
        /**
         * This fun is used to success response of apis
         */
        fun onSuccess(data: T?)

        /**
         * This fun is used to error response of apis
         */
        fun onError(throwable: Throwable)
    }

}