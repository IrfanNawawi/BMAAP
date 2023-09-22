package id.heycoding.submissiongithubuser.data.local.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.heycoding.submissiongithubuser.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class GithubUserDatabase : RoomDatabase() {

    abstract fun githubUserDao(): GithubUserDao

    companion object {
        @Volatile
        private var INSTANCE: GithubUserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): GithubUserDatabase {
            if (INSTANCE == null) {
                synchronized(GithubUserDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GithubUserDatabase::class.java,
                        "githubuser_database"
                    ).build()
                }
            }
            return INSTANCE as GithubUserDatabase
        }
    }
}