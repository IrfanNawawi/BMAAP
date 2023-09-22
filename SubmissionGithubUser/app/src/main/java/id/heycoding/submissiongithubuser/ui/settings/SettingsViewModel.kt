package id.heycoding.submissiongithubuser.ui.settings

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.heycoding.submissiongithubuser.util.SettingPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : ViewModel() {
    private val settingPreferences = SettingPreferences(application)

    fun getThemeSettings(): LiveData<Boolean> {
        return settingPreferences.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            settingPreferences.saveThemeSetting(isDarkModeActive)
        }
    }
}