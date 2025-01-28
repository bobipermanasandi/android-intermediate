package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.loginwithanimation.data.repository.RepositoryAuth
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfigAuth
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivitySignupBinding
import com.dicoding.picodiploma.loginwithanimation.view.view_model_factory.ViewModelFactoryAuth

class SignupActivity : AppCompatActivity() {

    // BINDING
    private lateinit var binding: ActivitySignupBinding

    // VIEW MODEL
    private val viewModel: SignUpViewModel by viewModels {
        ViewModelFactoryAuth(
            RepositoryAuth(
                UserPreference.getInstance(this),
                ApiConfigAuth.getApiService(getTokenUser())
            )
        )
    }

    // MENDAPATKAN TOKEN
    private fun getTokenUser(): String {
        val sharedPreferences = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString(USER_TOKEN, "") ?: ""
    }

    // PROGESS BAR
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        playAnimation()

        binding.signupButton.setOnClickListener {
            setupRegister()
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.registerResult.observe(this) {
            when (it) {
                is SignUpViewModel.RegisterResult.Success -> {
                    setupAction(it.response)
                }

                is SignUpViewModel.RegisterResult.Error -> {
                    setupAction(it.error)
                }

                is SignUpViewModel.RegisterResult.Loading -> {

                }
            }
        }
    }

    private fun setupRegister() {
        val name = binding.edRegisterName.text.toString()
        val email = binding.edRegisterEmail.text.toString()
        val password = binding.edRegisterPassword.text.toString()

        viewModel.register(name, email, password)
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction(message: String) {
        binding.signupButton.setOnClickListener {
            val email = binding.edRegisterEmail.text.toString()

            AlertDialog.Builder(this).apply {
                setTitle("Yeah!")
                setMessage(message)
                setPositiveButton("Lanjut") { _, _ ->
                    finish()
                }
                create()
                show()
            }
        }
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val tvName = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val etName = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val tvEmail = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val etEmail = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val tvPassword = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val etPassword = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(title, tvName, etName, tvEmail, etEmail, tvPassword, etPassword, signup)
            startDelay = 100
            start()
        }
    }

    companion object {
        private const val USER_PREF = "user_preferences"
        private const val USER_TOKEN = "user_token"
    }
}