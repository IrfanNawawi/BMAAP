package id.heycoding.submissiongithubuser.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.heycoding.submissiongithubuser.data.local.entity.UserEntity

@Dao
interface GithubUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userEntity: List<UserEntity>)

    @Query("DELETE FROM user WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT * FROM user")
    fun getAllUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun checkFavoriteUserById(id: Int): LiveData<UserEntity>

    @Query("SELECT * FROM user where bookmarked = 1")
    fun getFavoriteUser(): LiveData<List<UserEntity>>

    @Update
    fun updateNews(user: UserEntity)

    @Query("DELETE FROM user WHERE bookmarked = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM user WHERE login = :login AND bookmarked = 1)")
    fun isUserFavorited(login: String): Boolean
}