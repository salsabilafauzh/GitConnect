package com.example.gitconnect.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gitconnect.ui.DetailFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    private var username: String? = null
    override fun getItemCount(): Int {
        return 2
    }

    fun setSelectedProfile(selectedProfile: String?) {
        username = selectedProfile
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = DetailFragment()
        fragment.arguments = Bundle().apply {
            putInt(DetailFragment.ARG_SECTION_NUMBER, position + 1)
            putString(DetailFragment.ARG_USERNAME_PROFILE, username)
        }
        return fragment
    }

}