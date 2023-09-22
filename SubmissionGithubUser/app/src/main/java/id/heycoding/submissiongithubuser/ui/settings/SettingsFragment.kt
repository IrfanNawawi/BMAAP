package id.heycoding.submissiongithubuser.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import id.heycoding.submissiongithubuser.databinding.FragmentSettingsBinding
import id.heycoding.submissiongithubuser.ui.ViewModelFactory
import id.heycoding.submissiongithubuser.util.SettingPreferences

class SettingsFragment : Fragment() {

    private var _fragmentSettingsBinding: FragmentSettingsBinding? = null
    private val fragmentSettingsBinding get() = _fragmentSettingsBinding!!

    private val settingsViewModel by viewModels<SettingsViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    private lateinit var settingPreferences: SettingPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentSettingsBinding = FragmentSettingsBinding.inflate(layoutInflater)
        return fragmentSettingsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingPreferences = SettingPreferences(requireActivity())

        setupObserve()
    }

    /**
     * Function to set observe live data.
     */
    private fun setupObserve() {
        settingsViewModel.getThemeSettings()
            .observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    fragmentSettingsBinding.switchTheme.isChecked = true
                    true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    fragmentSettingsBinding.switchTheme.isChecked = false
                    false
                }
            }

        fragmentSettingsBinding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingsViewModel.saveThemeSetting(isChecked)
        }
    }

    /**
     * Function to destroy fragment.
     */
    override fun onDestroy() {
        super.onDestroy()
        _fragmentSettingsBinding = null
    }

}