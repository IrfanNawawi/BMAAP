package id.heycoding.submissiongithubuser.ui.search

import id.heycoding.submissiongithubuser.data.response.User

interface SearchUserCallback {
    fun setDetailUser(user: User)
}