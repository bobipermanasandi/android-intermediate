package com.dicoding.picodiploma.loginwithanimation.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.repository.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.response.DetailResponse
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: UserRepository): ViewModel() {

    private val _story = MutableLiveData<DetailResponse>()
    val story: LiveData<DetailResponse> = _story

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getStoryDetail(id: String) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getStoryDetail(id)
                _story.value = response
            } catch (e: Exception) {
                _error.value = DetailResponse(error = true, message = e.message).toString()
            } finally {
                _isLoading.value = false
            }
        }
    }
}