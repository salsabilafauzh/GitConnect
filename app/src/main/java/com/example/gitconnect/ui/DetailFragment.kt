package com.example.gitconnect.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitconnect.data.remote.response.ItemsItem
import com.example.gitconnect.databinding.FragmentDetailBinding
import com.example.gitconnect.ui.adapter.FollsAdapter
import com.example.gitconnect.ui.viewModel.DetailViewModel

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val detailViewModel: DetailViewModel by viewModels()
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())
        binding?.rvDetail?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding?.rvDetail?.addItemDecoration(itemDecoration)

        val username = arguments?.getString(ARG_USERNAME_PROFILE)
        if (username != null) {
            when (arguments?.getInt(ARG_SECTION_NUMBER)) {
                TAB_FOLLOWERS -> detailViewModel.loadFollowers(username)
                TAB_FOLLOWING -> detailViewModel.loadFollowing(username)
            }
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        detailViewModel.listFolls.observe(viewLifecycleOwner) {
            setViewData(it)
        }
    }

    private fun setViewData(data: List<ItemsItem>?) {
        if (!data.isNullOrEmpty()) {
            binding?.notFound?.visibility = View.INVISIBLE
            val adapter = FollsAdapter()
            adapter.submitList(data)
            binding?.rvDetail?.adapter = adapter
        } else {
            binding?.notFound?.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME_PROFILE = "selected_profile_username"
        const val TAB_FOLLOWERS = 1
        const val TAB_FOLLOWING = 2
    }
}