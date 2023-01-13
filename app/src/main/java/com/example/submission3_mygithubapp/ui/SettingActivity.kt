package com.example.submission3_mygithubapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submission3_mygithubapp.R
import com.example.submission3_mygithubapp.databinding.ActivitySettingBinding
import com.example.submission3_mygithubapp.model.SettingViewModel
import com.example.submission3_mygithubapp.model.SettingViewModelFactory
import com.example.submission3_mygithubapp.setting.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = resources.getString(R.string.setting)

        val pref = SettingPreferences.getInstance(dataStore)
        val SettingViewModel = ViewModelProvider(this,  SettingViewModelFactory(pref)).get(SettingViewModel::class.java)
        SettingViewModel.getThemeSettings().observe( this) { isDarkModeActive: Boolean ->
            binding.apply {
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    switchTheme.isChecked = false
                }
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            SettingViewModel.saveThemeSetting(isChecked)
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
}