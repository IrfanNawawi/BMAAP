package id.heycoding.submissiongithubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.heycoding.submissiongithubuser.ui.detail.followers.FollowersFragment
import id.heycoding.submissiongithubuser.ui.detail.following.FollowingFragment

class SectionsPagerAdapter(activity: FragmentActivity, private val username: Bundle) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowersFragment()
        }
        fragment?.arguments = username
        return fragment as Fragment
    }
}