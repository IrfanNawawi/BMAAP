package id.heycoding.submissiongithubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.heycoding.submissiongithubuser.data.local.entity.UserEntity
import id.heycoding.submissiongithubuser.domain.repository.GithubUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {

    private val mGithubUserRepository: GithubUserRepository = GithubUserRepository(application)

    /**
     * Function to get favorite user.
     */
    fun favoriteUser(): LiveData<List<UserEntity>> = mGithubUserRepository.getAllFavorites()
}