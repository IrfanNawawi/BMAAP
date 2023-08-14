package id.heycoding.submissiongithubuser.ui.detail.following

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import id.heycoding.submissiongithubuser.data.response.User
import id.heycoding.submissiongithubuser.databinding.FragmentFollowingBinding
import id.heycoding.submissiongithubuser.util.const.ARG_SECTION_USERNAME


class FollowingFragment : Fragment() {

    private var _fragmentFollowingBinding: FragmentFollowingBinding? = null
    private val fragmentFollowingBinding get() = _fragmentFollowingBinding!!
    private val followingViewModel: FollowingViewModel by viewModels()
    private lateinit var followingAdapter: FollowingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentFollowingBinding = FragmentFollowingBinding.inflate(inflater, container, false)
        followingAdapter = FollowingAdapter(ArrayList())
        return fragmentFollowingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserve()
    }

    private fun setupObserve() {
        followingViewModel.followingUser.observe(viewLifecycleOwner) { following ->
            setFollowingData(following)
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            setLoadingData(loading)
        }

        followingViewModel.getFollowingUser(arguments?.getString(ARG_SECTION_USERNAME).toString())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setFollowingData(userData: List<User>) {
        with(fragmentFollowingBinding) {
            val listUserData = ArrayList<User>()
            for (user in userData) {
                listUserData.clear()
                listUserData.addAll(userData)
            }

            with(rvFollowing) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                val followingAdapter = FollowingAdapter(listUserData)
                adapter?.notifyDataSetChanged()
                adapter = followingAdapter
            }
        }
    }

    private fun setLoadingData(loading: Boolean) {
        fragmentFollowingBinding.apply {
            if (loading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentFollowingBinding = null
    }
}