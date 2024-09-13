package com.example.lemmecook_frontend.utilities

import android.content.Context
import android.widget.Toast
import com.example.lemmecook_frontend.api.UsersApi
import com.example.lemmecook_frontend.models.response.AuthResponse
import com.example.lemmecook_frontend.models.response.UserInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.net.Uri
import com.example.lemmecook_frontend.components.ImageUtility
import com.example.lemmecook_frontend.models.request.UserInfoRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

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
//        fun updateUserInfo(
//            userId: String,
//            username: String,
//            fullName: String,
//            password: String,
//            newPassword: String,
//            avatarUri: Uri?,
//            context: Context,
//            onSuccess: (String) -> Unit
//        ) {
//            val updateUserInfoApi = ApiUtility.getApiClient().create(UsersApi::class.java)
//
//            // Convert fields to RequestBody
//            val userIdRequestBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())
//            val usernameRequestBody = username.toRequestBody("text/plain".toMediaTypeOrNull())
//            val fullNameRequestBody = fullName.toRequestBody("text/plain".toMediaTypeOrNull())
//            val passwordRequestBody = password.toRequestBody("text/plain".toMediaTypeOrNull())
//            val newPasswordRequestBody = newPassword.toRequestBody("text/plain".toMediaTypeOrNull())
//
//            // Prepare avatar if present
//            val avatarPart: MultipartBody.Part? = avatarUri?.let {
//                val file = ImageUtility.uriToFile(it, context)
//                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//                MultipartBody.Part.createFormData("avatar", file.name, requestFile)
//            }
//
//            // Make API call
//            updateUserInfoApi.updateUserInfo(
//                userIdRequestBody,
//                usernameRequestBody,
//                fullNameRequestBody,
//                passwordRequestBody,
//                newPasswordRequestBody,
//                avatarPart
//            ).enqueue(object : Callback<AuthResponse> {
//                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
//                    val statusResponse = response.body()
//                    if (response.isSuccessful && statusResponse?.status == "success") {
//                        Toast.makeText(context, "Update successful!", Toast.LENGTH_SHORT).show()
//                        onSuccess(statusResponse.status)
//                    } else {
//                        Toast.makeText(context, "Update failed: ${statusResponse?.status}", Toast.LENGTH_LONG).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
//                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
//                }
//            })
//        }

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

            // Create a UserInfoRequest object without avatar
            val userInfoRequest = UserInfoRequest(
                userId = userId,
                username = username,
                fullName = fullName,
                password = password,
                newPassword = newPassword
            )

            // Make API call
            updateUserInfoApi.updateUserInfo(userInfoRequest).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    val statusResponse = response.body()
                    if (response.isSuccessful && statusResponse?.status == "success") {
                        Toast.makeText(context, "Update successful!", Toast.LENGTH_SHORT).show()
                        onSuccess(statusResponse.status)
                    } else {
                        Toast.makeText(context, "Update failed: ${statusResponse?.status}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}

