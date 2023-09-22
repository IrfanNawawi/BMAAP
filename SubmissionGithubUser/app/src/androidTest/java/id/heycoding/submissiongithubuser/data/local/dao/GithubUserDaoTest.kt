package id.heycoding.submissiongithubuser.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import id.heycoding.submissiongithubuser.data.local.entity.UserEntity
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class GithubUserDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var githubUserDatabase: GithubUserDatabase
    lateinit var githubUserDao: GithubUserDao

    @Before
    fun setUp() {
        githubUserDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GithubUserDatabase::class.java
        ).allowMainThreadQueries().build()
        githubUserDao = githubUserDatabase.githubUserDao()
    }

    @Test
    fun insertUser() {
        val user = UserEntity(1, "IrfanNawawi", "avatarUrl")
        githubUserDao.insert(user)

        val result = githubUserDao.getAllFavoriteUser().getOrAwaitValue()
        assertEquals(1, result.size)
        assertEquals("IrfanNawawi", result[0].username)
    }

    @Test
    fun getUserById() {
        val user = UserEntity(3, "NawNaw", "avatarUrl")
        githubUserDao.insert(user)

        val result = githubUserDao.checkFavoriteUserById(3).getOrAwaitValue()
        assertEquals("NawNaw", result.username)
    }

    @Test
    fun deleteUser() {
        val user = UserEntity(1, "IrfanNawawi", "avatarUrl")
        githubUserDao.insert(user)
        githubUserDao.deleteById(1)

        val result = githubUserDao.getAllFavoriteUser().getOrAwaitValue()
        assertEquals(0, result.size)
    }

    @After
    fun tearDown() {
        githubUserDatabase.close()
    }

}