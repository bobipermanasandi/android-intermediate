package com.dicoding.picodiploma.loginwithanimation.data.repository

import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.response.DetailResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.StoryResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.StoryUploadResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    suspend fun getStory(): StoryResponse {
        return apiService.getStories()
    }

    suspend fun uploadStory(file: MultipartBody.Part, description: RequestBody): StoryUploadResponse {
        return apiService.uploadStory(file, description)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun getStoryDetail(id: String): DetailResponse {
        return apiService.getStoriesDetail(id)
    }

    companion object {
        fun getInstance(userPreference: UserPreference, apiService: ApiService) =
            UserRepository(userPreference, apiService)
    }
}