package id.heycoding.submissiongithubuser.ui.detail.followers

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import id.heycoding.submissiongithubuser.data.response.User
import id.heycoding.submissiongithubuser.databinding.FragmentFollowersBinding
import id.heycoding.submissiongithubuser.util.const.ARG_SECTION_USERNAME

class FollowersFragment : Fragment() {

    private var _fragmentFollowersBinding: FragmentFollowersBinding? = null
    private val fragmentFollowersBinding get() = _fragmentFollowersBinding!!
    private val followersViewModel: FollowersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentFollowersBinding = FragmentFollowersBinding.inflate(inflater, container, false)
        return fragmentFollowersBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserve()
    }

    private fun setupObserve() {
        followersViewModel.followersUser.observe(viewLifecycleOwner) { Followers ->
            setFollowersData(Followers)
        }

        followersViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            setLoadingData(loading)
        }

        followersViewModel.getFollowersUser(arguments?.getString(ARG_SECTION_USERNAME).toString())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setFollowersData(userData: List<User>) {
        with(fragmentFollowersBinding) {
            val listUserData = ArrayList<User>()
            for (user in userData) {
                listUserData.clear()
                listUserData.addAll(userData)
            }

            with(rvFollowers) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                val followersAdapter = FollowersAdapter(listUserData)
                adapter?.notifyDataSetChanged()
                adapter = followersAdapter
            }
        }
    }

    private fun setLoadingData(loading: Boolean) {
        fragmentFollowersBinding.apply {
            if (loading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentFollowersBinding = null
    }
}