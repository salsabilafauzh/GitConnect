package com.example.gitconnect.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitconnect.data.remote.response.ItemsItem
import com.example.gitconnect.databinding.ItemProfileBinding

class UserAdapter :
    ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var itemClickListener: OnItemClickListener
    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    interface OnItemClickListener {
        fun onItemClick(profile: ItemsItem)
    }

    class MyViewHolder(private val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: ItemsItem) {
            binding.tvUsername.text = profile.login
            Glide.with(binding.root)
                .load(profile.avatarUrl)
                .into(binding.circleImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val profile = getItem(position)
        holder.bind(profile)
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(profile)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}