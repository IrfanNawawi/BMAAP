package id.heycoding.submissiongithubuser.domain.repository

import android.app.Application
import androidx.lifecycle.LiveData
import id.heycoding.submissiongithubuser.data.local.dao.GithubUserDao
import id.heycoding.submissiongithubuser.data.local.dao.GithubUserDatabase
import id.heycoding.submissiongithubuser.data.local.entity.UserEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GithubUserRepository(application: Application) {
    private val mGithubUserDao: GithubUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = GithubUserDatabase.getDatabase(application)
        mGithubUserDao = db.githubUserDao()
    }

    fun insert(userEntity: UserEntity) {
        executorService.execute { mGithubUserDao.insert(userEntity) }
    }

    fun delete(id: Int) {
        executorService.execute { mGithubUserDao.deleteById(id) }
    }

    fun getAllFavorites(): LiveData<List<UserEntity>> = mGithubUserDao.getAllFavoriteUser()

    fun getFavoriteUserById(id: Int): LiveData<UserEntity> =
        mGithubUserDao.checkFavoriteUserById(id)
}