package id.heycoding.submissiongithubuser.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.heycoding.submissiongithubuser.util.SettingPreferences

class MainViewModel(application: Application) : ViewModel() {
    private val settingPreferences = SettingPreferences(application)

    fun getThemeSettings(): LiveData<Boolean> {
        return settingPreferences.getThemeSetting().asLiveData()
    }
}