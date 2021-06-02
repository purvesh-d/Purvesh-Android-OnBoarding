package com.onboarding.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.onboarding.base.BaseViewModel
import com.onboarding.data.remote.APIInterface
import com.onboarding.model.LoginRequestModel
import com.onboarding.model.LoginResponseModel
import com.onboarding.util.ERROR_EMAIL
import com.onboarding.util.ERROR_EMAIL_FORMAT
import com.onboarding.util.ERROR_PASSWORD
import com.onboarding.util.INVALID_EMAIL_PASS
import com.onboarding.util.YOUR_TOKEN_IS
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityMainViewModel @Inject constructor(private val apiService: APIInterface) : BaseViewModel() {
    var txtEmail = MutableLiveData<String>()
    var txtppassword = MutableLiveData<String>()
    var apiResponse = MutableLiveData<String>()

    fun isValidDetails(): Boolean {
        if (TextUtils.isEmpty(txtEmail.value)) {
            apiResponse.value = ERROR_EMAIL
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(txtEmail.value).matches()) {
            apiResponse.value = ERROR_EMAIL_FORMAT
            return false
        } else if (TextUtils.isEmpty(txtppassword.value)) {
            apiResponse.value = ERROR_PASSWORD
            return false
        }
        return true
    }

    fun doLogin() {
        call(apiService.loginAsync(LoginRequestModel(txtEmail.value.toString(), txtppassword.value.toString())), object : ApiCallbackListener<LoginResponseModel> {
            override fun onSuccess(data: LoginResponseModel?) {
                apiResponse.value = YOUR_TOKEN_IS + data?.token.toString()
            }

            override fun onError(throwable: Throwable) {
                apiResponse.value = INVALID_EMAIL_PASS
            }
        })
    }
}