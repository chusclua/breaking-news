package com.chus.clua.breakingnews.presentation.features.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.chus.clua.breakingnews.databinding.ActivityNewsDetailBinding
import com.chus.clua.breakingnews.domain.model.News
import com.chus.clua.breakingnews.presentation.binding.BackButtonHandler

class NewsDetailActivity: AppCompatActivity(), BackButtonHandler {

    private lateinit var binding: ActivityNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.item = intent.getParcelableExtra(MODEL)
        binding.handler = this

    }

    override fun onBackButtonClicked() {
        super.onBackPressed()
    }

    companion object {
        private const val MODEL = "detail_model"
        fun start(context: Activity, model: News, imageView: AppCompatImageView) {
            Intent(context, NewsDetailActivity::class.java).apply {
                putExtra(MODEL, model)
            }.run {
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context, imageView, ViewCompat.getTransitionName(imageView)!!
                ).let { context.startActivity(this, it.toBundle()) }
            }
        }
    }
}