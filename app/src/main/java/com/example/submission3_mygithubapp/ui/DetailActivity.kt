package com.example.submission3_mygithubapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.submission3_mygithubapp.data.Result
import com.bumptech.glide.Glide
import com.example.submission3_mygithubapp.R
import com.example.submission3_mygithubapp.adapter.SectionsPagerAdapter
import com.example.submission3_mygithubapp.databinding.ActivityDetailBinding
import com.example.submission3_mygithubapp.model.DetailViewModel
import com.example.submission3_mygithubapp.model.FavoriteViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: FavoriteViewModelFactory = FavoriteViewModelFactory.getInstance(this)
        val detailViewModel: DetailViewModel by viewModels {
            factory
        }

        val username = intent.getStringExtra(EXTRA_DETAIL) as String
        detailViewModel.getDetailUser(username)

        detailViewModel.getDetailUser(username).observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.apply {
                            progressBar.visibility = View.GONE
                            val user = it.data
                            Glide.with(this@DetailActivity)
                                .load(user.avatarUrl)
                                .circleCrop()
                                .into(binding.dtlAvatar)
                            val actionbar = supportActionBar
                            actionbar!!.title = user.login
                            dtlFollowers.text = "${user.followers} Followers"
                            dtlFollowing.text = "${user.following} Following"
                            dtlRepo.text = "${user.publicrepos} Repository"
                            if (user.name != null) {
                                dtlName.text = user.name
                            } else {
                                dtlName.text = user.login
                            }

                            if (user.isFavorite) {
                                btnFavorite.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        binding.btnFavorite.context,
                                        R.drawable.ic_favorite_true
                                    )
                                )
                            } else {
                                btnFavorite.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        binding.btnFavorite.context,
                                        R.drawable.ic_favorite
                                    )
                                )
                            }

                            btnFavorite.setOnClickListener {
                                if (user.isFavorite) {
                                    detailViewModel.deleteFavorite(user)
                                } else {
                                    detailViewModel.addFavorite(user)
                                }
                            }

                            val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity)
                            sectionsPagerAdapter.username = user.login

                            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
                            viewPager.adapter = sectionsPagerAdapter

                            val tabs: TabLayout = findViewById(R.id.tab_layout)
                            TabLayoutMediator(tabs, viewPager) { tab, position ->
                                tab.text = resources.getString(TAB_TITLES[position])
                            }.attach()
                        }
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}