package id.heycoding.submissiongithubuser.di

import android.content.Context
import id.heycoding.submissiongithubuser.data.UserRepository
import id.heycoding.submissiongithubuser.data.local.dao.GithubUserDatabase
import id.heycoding.submissiongithubuser.data.remote.service.ApiConfig
import id.heycoding.submissiongithubuser.util.AppExecutors

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiServices = ApiConfig.getApiServices()
        val database = GithubUserDatabase.getDatabase(context)
        val dao = database.githubUserDao()
        val appExecutors = AppExecutors()
        return UserRepository.getInstance(apiServices, dao, appExecutors)
    }
}