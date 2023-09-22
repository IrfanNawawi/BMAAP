package id.heycoding.submissiongithubuser.ui.detail.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import id.heycoding.submissiongithubuser.data.remote.response.User
import id.heycoding.submissiongithubuser.databinding.FragmentFollowingBinding
import id.heycoding.submissiongithubuser.util.Const.ARG_SECTION_USERLOGIN


class FollowingFragment : Fragment() {

    private val followingViewModel: FollowingViewModel by viewModels()

    private var _fragmentFollowingBinding: FragmentFollowingBinding? = null
    private val fragmentFollowingBinding get() = _fragmentFollowingBinding!!

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

    /**
     * Function to set observe live data.
     */
    private fun setupObserve() {
        followingViewModel.followingUser.observe(viewLifecycleOwner) { following ->
            val listUserData = ArrayList<User>()
            for (user in following) {
                listUserData.clear()
                listUserData.addAll(following)
            }

            with(fragmentFollowingBinding.rvFollowing) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                val followingAdapter = FollowingAdapter(listUserData)
                adapter?.notifyDataSetChanged()
                adapter = followingAdapter
            }
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            setLoadingData(loading)
        }

        followingViewModel.getFollowingUser(arguments?.getString(ARG_SECTION_USERLOGIN).toString())
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