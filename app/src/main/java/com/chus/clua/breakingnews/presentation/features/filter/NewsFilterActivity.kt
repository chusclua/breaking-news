package com.chus.clua.breakingnews.presentation.features.filter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chus.clua.breakingnews.R
import com.chus.clua.breakingnews.databinding.ActivityNewsFilterBinding
import com.chus.clua.breakingnews.domain.model.Category
import com.chus.clua.breakingnews.domain.model.Country
import com.chus.clua.breakingnews.presentation.binding.NewsFilterHandler
import com.chus.clua.breakingnews.presentation.views.EntityListDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFilterActivity : AppCompatActivity(), NewsFilterHandler {

    private var countriesDialogFragment: EntityListDialogFragment<Country>? = null
    private var categoriesDialogFragment: EntityListDialogFragment<Category>? = null

    private lateinit var binding: ActivityNewsFilterBinding
    private val viewModel: NewsFilterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.lifecycleOwner = this
        binding.handler = this
        binding.viewmodel = viewModel

        observeViewModel()
        viewModel.load()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, { countries ->
            renderCountriesDialog(countries)
        })
        viewModel.categories.observe(this, { categories ->
            renderCategoriesDialog(categories)
        })
    }

    private fun renderCountriesDialog(countries: List<Country>) {
        countriesDialogFragment = EntityListDialogFragment(
            getString(R.string.country_list_dialog_title),
            countries
        ) { selectedCountry ->
            viewModel.setCountry(selectedCountry)
        }
    }

    private fun renderCategoriesDialog(categories: List<Category>) {
        categoriesDialogFragment = EntityListDialogFragment(
            getString(R.string.category_list_dialog_title),
            categories
        ) { selectedCategory ->
            viewModel.setCategory(selectedCategory)
        }
    }

    override fun onShowCountriesButtonClicked() {
        countriesDialogFragment?.show(supportFragmentManager, EntityListDialogFragment.TAG)
    }

    override fun onShowCategoriesButtonClicked() {
        categoriesDialogFragment?.show(supportFragmentManager, EntityListDialogFragment.TAG)
    }

    override fun onSubmitButtonClicked() {
        Intent().apply {
            putExtra(FILTER, viewModel.filter())
        }.run {
            setResult(RESULT_OK, this)
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_hold, R.anim.anim_right_side_out)
    }

    companion object {
        const val FILTER = "filter"
        fun start(context: Activity) = Intent(context, NewsFilterActivity::class.java)
    }
}