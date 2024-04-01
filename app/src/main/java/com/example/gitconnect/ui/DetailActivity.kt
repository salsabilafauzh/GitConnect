package com.example.gitconnect.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.gitconnect.R
import com.example.gitconnect.data.local.database.FavoriteUserEntity
import com.example.gitconnect.helper.ViewModelFactory
import com.example.gitconnect.data.remote.response.DetailUserResponse
import com.example.gitconnect.databinding.ActivityDetailBinding
import com.example.gitconnect.ui.adapter.SectionPagerAdapter
import com.example.gitconnect.ui.viewModel.DetailViewModel
import com.example.gitconnect.ui.viewModel.FavoriteViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var dataUser: FavoriteUserEntity
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(
            application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_PROFILE)
        setPager(username)

        detailViewModel.isLoading.observe(this@DetailActivity) {
            showLoading(it)
        }
        detailViewModel.selectedProfile.observe(this@DetailActivity) {
            setViewData(it)
        }

        if (username != null) {
            detailViewModel.getDetailProfile(username)
            favoriteViewModel.checkIsFavorite(username).observe(this@DetailActivity) { userEntity ->
                Log.d("test", "$userEntity")
                if (userEntity != null) {
                    binding.floatingActionButton.setImageResource(R.drawable.ic_favorite)
                    binding.floatingActionButton.setOnClickListener {
                        favoriteViewModel.deleteFavorite(dataUser)
                    }
                } else {
                    binding.floatingActionButton.setImageResource(R.drawable.ic_favorite_border)
                    binding.floatingActionButton.setOnClickListener {
                        favoriteViewModel.addFavorite(dataUser)
                    }
                }
            }
        }
    }

    private fun setPager(username: String?) {
        val sectionPagerAdapter = SectionPagerAdapter(this@DetailActivity)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        sectionPagerAdapter.setSelectedProfile(username)
    }

    private fun setViewData(data: DetailUserResponse) {
        Glide.with(binding.root)
            .load(data.avatarUrl)
            .into(binding.circleImageDetail)
        binding.tvUsername.text = data.name
        binding.tvName.text = data.login
        binding.tvFollowers.text = "${data.followers} Followers"
        binding.tvFollowing.text = "${data.following} Following"
        dataUser = FavoriteUserEntity(data.login.toString(), data.avatarUrl.toString())
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_PROFILE = "extra_profile"
    }
}