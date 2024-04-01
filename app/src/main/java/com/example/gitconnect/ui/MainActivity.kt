package com.example.gitconnect.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitconnect.R
import com.example.gitconnect.data.remote.response.ItemsItem
import com.example.gitconnect.databinding.ActivityMainBinding
import com.example.gitconnect.ui.adapter.UserAdapter
import com.example.gitconnect.ui.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rcProfile.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rcProfile.addItemDecoration(itemDecoration)
        mainViewModel.setUsername(default_query)

        mainViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        mainViewModel.profileList.observe(this) { listData ->
            setProfileData(listData)
        }
        mainViewModel.uname.observe(this) {
            mainViewModel.loadProfile(it)
        }

        with(binding) {
            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.favorite -> {
                        startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
                        true
                    }

                    R.id.setting -> {
                        startActivity(Intent(this@MainActivity, SettingActivity::class.java))
                        true
                    }

                    else -> {
                        true
                    }
                }
            }
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    if (searchView.text.toString().isNotEmpty()) {
                        mainViewModel.setUsername(searchView.text.toString())
                    } else {
                        mainViewModel.setUsername(default_query)
                    }
                    false
                }
        }
    }

    private fun setProfileData(listData: List<ItemsItem?>?) {
        val adapter = UserAdapter()
        adapter.submitList(listData)
        binding.rcProfile.adapter = adapter

        adapter.setOnClickListener(object : UserAdapter.OnItemClickListener {
            override fun onItemClick(profile: ItemsItem) {
                showSelected(profile.login)
            }
        })
        binding.notFound.visibility =
            if (listData?.isEmpty() == true) View.VISIBLE else View.INVISIBLE
    }

    private fun showSelected(username: String?) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_PROFILE, username)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        const val default_query = "arif"
    }
}