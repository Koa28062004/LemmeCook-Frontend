package com.example.lemmecook_frontend.utilities

import android.content.Context
import android.widget.Toast
import com.example.lemmecook_frontend.api.UsersApi
import com.example.lemmecook_frontend.models.response.UserInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserApiUtility {
    companion object {
        // Get the User Information
        fun getUserInfo(userId: String, context: Context, onSuccess: (UserInfoResponse) -> Unit) {
            val getUserInfoApi = ApiUtility.getApiClient().create(UsersApi::class.java)

            getUserInfoApi.getUserInfo(userId).enqueue(object : Callback<UserInfoResponse> {
                override fun onResponse(
                    call: Call<UserInfoResponse>,
                    response: Response<UserInfoResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val userInfo = response.body()!!
                        Toast.makeText(context, "Data fetched successfully", Toast.LENGTH_SHORT).show()
                        onSuccess(userInfo)
                    } else {
                        Toast.makeText(context, "Failed to get data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
