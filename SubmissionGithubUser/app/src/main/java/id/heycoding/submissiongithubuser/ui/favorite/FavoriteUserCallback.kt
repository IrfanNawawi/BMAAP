package id.heycoding.submissiongithubuser.ui.favorite

import id.heycoding.submissiongithubuser.data.local.entity.UserEntity

interface FavoriteUserCallback {
    fun setIntentDetailUser(userEntity: UserEntity)
}