package com.dicoding.picodiploma.loginwithanimation.data.repository

import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.response.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiServiceAuth

class RepositoryAuth (
     private val userPreference: UserPreference,
     private val apiServiceAuth: ApiServiceAuth
) {

    suspend fun registerUser(name: String, email: String, password: String): RegisterResponse {
        return apiServiceAuth.register(name, email, password)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val response = apiServiceAuth.login(email, password)
        //udh login
        if (!response.error!!) {
            response.loginResult?.token?.let {
                userPreference.saveSession(UserModel(email, it, true))
            }
        }
        return response
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    companion object {
        fun getInstance(userPreference: UserPreference, apiServiceAuth: ApiServiceAuth) =
            RepositoryAuth(userPreference, apiServiceAuth)
    }
}

