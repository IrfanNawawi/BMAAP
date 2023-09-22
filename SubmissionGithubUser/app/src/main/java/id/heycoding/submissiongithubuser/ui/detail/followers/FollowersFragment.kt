package id.heycoding.submissiongithubuser.ui.detail.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import id.heycoding.submissiongithubuser.data.remote.response.User
import id.heycoding.submissiongithubuser.databinding.FragmentFollowersBinding
import id.heycoding.submissiongithubuser.util.Const.ARG_SECTION_USERLOGIN

class FollowersFragment : Fragment() {

    private val followersViewModel: FollowersViewModel by viewModels()

    private var _fragmentFollowersBinding: FragmentFollowersBinding? = null
    private val fragmentFollowersBinding get() = _fragmentFollowersBinding!!

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

    /**
     * Function to set observe live data.
     */
    private fun setupObserve() {
        followersViewModel.followersUser.observe(viewLifecycleOwner) { followers ->
            val listUserData = ArrayList<User>()
            for (user in followers) {
                listUserData.clear()
                listUserData.addAll(followers)
            }

            with(fragmentFollowersBinding.rvFollowers) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                val followersAdapter = FollowersAdapter(listUserData)
                adapter?.notifyDataSetChanged()
                adapter = followersAdapter
            }
        }

        followersViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            setLoadingData(loading)
        }

        followersViewModel.getFollowersUser(arguments?.getString(ARG_SECTION_USERLOGIN).toString())
    }

    /**
     * Function to set loading data.
     */
    private fun setLoadingData(loading: Boolean) {
        fragmentFollowersBinding.apply {
            if (loading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    /**
     * Function to destroy fragment.
     */
    override fun onDestroy() {
        super.onDestroy()
        _fragmentFollowersBinding = null
    }
}