package com.dicoding.picodiploma.loginwithanimation.view.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.response.Story
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailBinding
import com.dicoding.picodiploma.loginwithanimation.view.view_model_factory.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idStory = intent.getStringExtra(STORY_ID)

        if (idStory != null) {
            viewModel.getStoryDetail(idStory)
        }

        viewModel.story.observe(this) {
            if (!it.error!!) {
                it.story?.let {
                    setDetailData(it)
                }
            } else {
                binding.tvJudul.text = ""
                binding.tvDeskripsi.text = ""
            }
        }

        viewModel.error.observe(this) {
            binding.tvJudul.text = ""
            binding.tvDeskripsi.text = it
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setDetailData(story: Story) {
        binding.tvJudul.text = story.name
        binding.tvDeskripsi.text = story.description
        Glide.with(binding.root)
            .load(story.photoUrl)
            .into(binding.ivGambarStory)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val STORY_ID = "story_id"
    }
}