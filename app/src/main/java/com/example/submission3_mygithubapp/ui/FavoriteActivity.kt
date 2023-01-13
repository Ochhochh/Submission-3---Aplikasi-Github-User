package com.example.submission3_mygithubapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3_mygithubapp.R
import com.example.submission3_mygithubapp.databinding.ActivityFavoriteBinding
import com.example.submission3_mygithubapp.adapter.ListUserAdapter
import com.example.submission3_mygithubapp.api.User
import com.example.submission3_mygithubapp.model.FavoriteViewModel
import com.example.submission3_mygithubapp.model.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = resources.getString(R.string.favorite)

        val factory: FavoriteViewModelFactory = FavoriteViewModelFactory.getInstance(this)
        val viewModel: FavoriteViewModel by viewModels {
            factory
        }

        viewModel.getFavoriteUser().observe(this) { item ->
            binding.progressBar.visibility = View.GONE
            val listUser = item.map {
                User(it.login, it.avatarUrl)
            }

            val adapter = ListUserAdapter(listUser as ArrayList<User>)
            binding.apply{
                rvUser.adapter = adapter
                rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
                rvUser.setHasFixedSize(true)
            }
            adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: User) {
                    Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_DETAIL, data.login)
                        startActivity(it)
                    }
                }
            })
        }
    }
}