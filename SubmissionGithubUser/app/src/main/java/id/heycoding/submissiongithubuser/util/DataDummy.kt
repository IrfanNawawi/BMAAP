package id.heycoding.submissiongithubuser.util

import id.heycoding.submissiongithubuser.data.local.entity.UserEntity

object DataDummy {
    fun generateDummyUser(): ArrayList<UserEntity> {
        val user = ArrayList<UserEntity>()

        user.add(
            UserEntity(
                4090245,
                "sidiqpermana",
                "https://avatars.githubusercontent.com/u/4090245?v=4",
            )
        )
        user.add(
            UserEntity(
                4090245,
                "sidiqpermana",
                "https://avatars.githubusercontent.com/u/4090245?v=4",
            )
        )
        user.add(
            UserEntity(
                4090245,
                "sidiqpermana",
                "https://avatars.githubusercontent.com/u/4090245?v=4",
            )
        )
        return user
    }
}