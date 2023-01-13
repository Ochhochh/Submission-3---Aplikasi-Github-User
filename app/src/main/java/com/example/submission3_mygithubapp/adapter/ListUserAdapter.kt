package com.example.submission3_mygithubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission3_mygithubapp.api.User
import com.example.submission3_mygithubapp.databinding.ItemRowUserBinding

class ListUserAdapter (private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {


    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(user: User) {

            binding.tvUsername.text = user.login
            Glide.with(itemView)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.imgItemPhoto)
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding(listUser[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(
                listUser[holder.adapterPosition]
            )
        }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback{
        fun onItemClicked(data: User)
    }
}