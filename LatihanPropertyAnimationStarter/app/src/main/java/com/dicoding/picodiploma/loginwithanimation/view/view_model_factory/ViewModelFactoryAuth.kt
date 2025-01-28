package com.dicoding.picodiploma.loginwithanimation.view.view_model_factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.loginwithanimation.data.repository.RepositoryAuth
import com.dicoding.picodiploma.loginwithanimation.di.InjectionAuth
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginViewModel
import com.dicoding.picodiploma.loginwithanimation.view.signup.SignUpViewModel

class ViewModelFactoryAuth(private val repository: RepositoryAuth) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object{
        fun getInstance(context: Context) = ViewModelFactoryAuth(InjectionAuth.provideRepositoryAuth(context))
    }
}