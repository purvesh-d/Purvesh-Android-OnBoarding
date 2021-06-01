package com.onboarding.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.onboarding.base.BaseViewModel
import com.onboarding.data.remote.APIInterface
import com.onboarding.model.UserModel
import com.onboarding.util.ERROR_EMAIL
import com.onboarding.util.ERROR_EMAIL_FORMAT
import com.onboarding.util.ERROR_PASSWORD
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ActivityMainViewModel @Inject constructor(private val apiService: APIInterface): BaseViewModel() {
    var txtEmail = MutableLiveData<String>()
    var txtppassword = MutableLiveData<String>()
    var apiResponse = MutableLiveData<String>()

    fun isValidDetails(): Boolean {
        if (TextUtils.isEmpty(txtEmail.value)) {
            apiResponse.value = ERROR_EMAIL
            return false
        }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(txtEmail.value).matches()) {
            apiResponse.value = ERROR_EMAIL_FORMAT
            return false
        }else if (TextUtils.isEmpty(txtppassword.value)) {
            apiResponse.value = ERROR_PASSWORD
            return false
        }
        return true
    }

    fun doLogin(){
        apiService.getUser().apply {
            enqueue(object : Callback<UserModel> {
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    Log.i("tag",response.message())
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Log.i("tag2",t.localizedMessage)
                }

            })
        }

    }
}