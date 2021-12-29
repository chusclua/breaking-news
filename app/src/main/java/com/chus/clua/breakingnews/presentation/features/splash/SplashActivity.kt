package com.chus.clua.breakingnews.presentation.features.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chus.clua.breakingnews.databinding.ActivitySplashBinding
import com.chus.clua.breakingnews.presentation.features.list.NewsListActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimation()

    }

    private fun startAnimation() {
        binding.splashView.animate()
            .alpha(1f)
            .setDuration(2000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    NewsListActivity.start(this@SplashActivity)
                    onBackPressed()
                }
            })
    }
}
