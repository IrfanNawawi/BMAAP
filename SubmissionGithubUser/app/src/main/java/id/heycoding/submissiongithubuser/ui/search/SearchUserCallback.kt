package id.heycoding.submissiongithubuser.ui.search

import id.heycoding.submissiongithubuser.data.remote.response.User

interface SearchUserCallback {
    fun setIntentDetailUser(user: User)
}