package com.dicoding.picodiploma.loginwithanimation.view.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.repository.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.response.StoryUploadResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadViewModel(private val repository: UserRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun uploadStory(file: MultipartBody.Part, description: RequestBody) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.uploadStory(file, description)
            } catch (e: Exception) {
                _error.value = StoryUploadResponse(error = true, message = e.message!!).toString()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getToken() {
        viewModelScope.launch {
            repository.getSession()
        }
    }
}