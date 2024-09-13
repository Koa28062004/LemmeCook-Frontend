package com.example.lemmecook_frontend.utilities

import android.content.Context
import android.widget.Toast
import com.example.lemmecook_frontend.activities.NavHost.Blog
import com.example.lemmecook_frontend.activities.NavHost.navigateTo
import com.example.lemmecook_frontend.api.UsersApi
import com.example.lemmecook_frontend.models.request.UserInfoRequest
import com.example.lemmecook_frontend.models.response.AuthResponse
import com.example.lemmecook_frontend.models.response.UserInfoResponse
import com.example.lemmecook_frontend.singleton.UserSession
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

        // Update the User Information
        fun updateUserInfo(
            userId: String,
            username: String,
            fullName: String,
            password: String,
            newPassword: String,
            context: Context,
            onSuccess: (String) -> Unit
        ) {
            val updateUserInfoApi = ApiUtility.getApiClient().create(UsersApi::class.java)

            val userInfoRequest = UserInfoRequest(
                userId = userId,
                username = username,
                fullName = fullName,
                password = password,
                newPassword = newPassword
            )

            updateUserInfoApi.updateUserInfo(userInfoRequest).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    val statusResponse = response.body()
                    if (response.isSuccessful) {
                        if (statusResponse?.status == "success") {
                            Toast.makeText(context, "Updated successful!", Toast.LENGTH_SHORT).show()
                            val status = response.body()?.status ?: "Unknown"
                            onSuccess(status)
                        } else {
                            Toast.makeText(context, "Login failed: ${statusResponse?.status}", Toast.LENGTH_LONG).show()
                        }
                    }
                    else {
                        Toast.makeText(context, "2 - Login failed: ${response.message()}", Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
