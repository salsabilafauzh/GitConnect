package com.example.gitconnect.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitconnect.data.local.database.FavoriteUserEntity
import com.example.gitconnect.helper.ViewModelFactory
import com.example.gitconnect.data.remote.response.ItemsItem
import com.example.gitconnect.databinding.ActivityFavoriteBinding
import com.example.gitconnect.ui.adapter.UserAdapter
import com.example.gitconnect.ui.viewModel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteViewModel = obtainViewModel(this@FavoriteActivity)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)
        favoriteViewModel.getAllFavorite().observe(this) { userList ->
            setFavoriteData(userList)
        }
    }

    private fun setFavoriteData(userList: List<FavoriteUserEntity>) {
        val adapter = UserAdapter()
        val items = arrayListOf<ItemsItem>()
        userList.map {
            val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
            items.add(item)
        }
        adapter.submitList(items)
        binding.rvFavorite.adapter = adapter
        adapter.setOnClickListener(object : UserAdapter.OnItemClickListener {
            override fun onItemClick(profile: ItemsItem) {
                showSelected(profile.login)
            }
        })
        binding.notFound.visibility = if (userList.isEmpty()) View.VISIBLE else View.INVISIBLE
    }

    private fun showSelected(username: String?) {
        val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_PROFILE, username)
        startActivity(intent)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }
}