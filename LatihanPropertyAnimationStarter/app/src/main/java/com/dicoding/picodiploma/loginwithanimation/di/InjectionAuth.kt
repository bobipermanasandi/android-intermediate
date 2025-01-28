package com.dicoding.picodiploma.loginwithanimation.di

import android.content.Context
import com.dicoding.picodiploma.loginwithanimation.data.repository.RepositoryAuth
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfigAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object InjectionAuth {

    fun provideRepositoryAuth(context: Context): RepositoryAuth {
        val pref = UserPreference.getInstance(context)
        val user = runBlocking {
            pref.getSession().first()
        }
        val apiService = ApiConfigAuth.getApiService(user.token)
        return RepositoryAuth.getInstance(pref, apiService)
    }
}
