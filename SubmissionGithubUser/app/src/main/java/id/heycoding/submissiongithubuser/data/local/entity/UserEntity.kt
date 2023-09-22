package id.heycoding.submissiongithubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "username")
    var username: String? = null,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null,

    @ColumnInfo(name = "following")
    var following: Int? = 0,

    @ColumnInfo(name = "followers")
    var followers: Int? = 0,

    @ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean
) : Parcelable
