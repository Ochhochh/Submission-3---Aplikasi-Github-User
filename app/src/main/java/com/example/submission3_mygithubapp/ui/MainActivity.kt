package com.example.submission3_mygithubapp.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3_mygithubapp.R
import com.example.submission3_mygithubapp.api.User
import com.example.submission3_mygithubapp.adapter.ListUserAdapter
import com.example.submission3_mygithubapp.model.MainViewModel
import com.example.submission3_mygithubapp.databinding.ActivityMainBinding
import com.example.submission3_mygithubapp.model.SettingViewModel
import com.example.submission3_mygithubapp.model.SettingViewModelFactory
import com.example.submission3_mygithubapp.setting.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val SettingViewModel = ViewModelProvider(this,  SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java)
        SettingViewModel.getThemeSettings().observe( this) { isDarkModeActive: Boolean ->
            binding.apply {
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        this.title = resources.getString(R.string.App)
        listDisplay()

        //SearchUser
        binding.etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { view, id, _ ->
            if (id == EditorInfo.IME_ACTION_SEARCH) {
                val textSearch = view.text.toString()
                viewModel.getSearchUser(textSearch)
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })

    }

    private fun listDisplay() {
        viewModel.User.observe(this) { user ->
            adapter = ListUserAdapter(user)
            binding.rvUser.adapter = adapter
            binding.rvUser.layoutManager = LinearLayoutManager(this)
            adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: User) {
                    Intent(this@MainActivity, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_DETAIL, data.login)
                        startActivity(it)
                    }
                }
            })
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.setting -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }
}