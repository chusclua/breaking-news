package com.chus.clua.breakingnews.presentation.features.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.chus.clua.breakingnews.R
import com.chus.clua.breakingnews.databinding.ActivityNewsListBinding
import com.chus.clua.breakingnews.domain.model.Country
import com.chus.clua.breakingnews.domain.model.News
import com.chus.clua.breakingnews.presentation.binding.ItemClickHandler
import com.chus.clua.breakingnews.presentation.features.detail.NewsDetailActivity
import com.chus.clua.breakingnews.presentation.features.filter.NewsFilterActivity
import com.chus.clua.breakingnews.presentation.features.list.adapter.NewsAdapter
import com.chus.clua.breakingnews.presentation.models.Filter
import com.chus.clua.breakingnews.presentation.models.NewsList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListActivity : AppCompatActivity(), ItemClickHandler<News> {

    private val articlesAdapter by lazy { NewsAdapter(this) }
    private val viewModel: NewsListViewModel by viewModels()
    private lateinit var binding: ActivityNewsListBinding

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val filter = result.data?.getParcelableExtra<Filter>(NewsFilterActivity.FILTER)
                viewModel.load(filter?.country, filter?.category)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initRecyclerView()
        observeViewModel()
        viewModel.load(Country.getDefault())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.news_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_news -> {
                resultLauncher.launch(NewsFilterActivity.start(this))
                overridePendingTransition(R.anim.anim_right_side_in, R.anim.anim_hold)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeViewModel() {
        viewModel.newsWrapper.observe(this, { newsWrapper ->
            bindTitle(newsWrapper.name)
            bindNews(newsWrapper.news)
        })
    }

    private fun bindTitle(wrapperName: String?) {
        title = wrapperName + " " + getString(R.string.app_name)
    }

    private fun bindNews(wrapperNews: List<NewsList>) {
        articlesAdapter.submitList(wrapperNews)
    }

    private fun initRecyclerView() {
        binding.rvNewsList.apply {
            adapter = articlesAdapter
        }
    }

    override fun onItemClicked(item: News, imageView: AppCompatImageView) {
        NewsDetailActivity.start(this, item, imageView)
    }

    companion object {
        fun start(context: Activity) = Intent(context, NewsListActivity::class.java).run {
            context.startActivity(this)
        }
    }
}