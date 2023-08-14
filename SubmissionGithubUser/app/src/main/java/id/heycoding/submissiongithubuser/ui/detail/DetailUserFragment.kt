package id.heycoding.submissiongithubuser.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import id.heycoding.submissiongithubuser.data.response.DetailUserResponse
import id.heycoding.submissiongithubuser.databinding.FragmentDetailUserBinding
import id.heycoding.submissiongithubuser.util.const.ARG_SECTION_USERNAME
import id.heycoding.submissiongithubuser.util.const.TAB_TITLES_DETAIL

class DetailUserFragment : Fragment() {

    private var _fragmentDetailUserBinding: FragmentDetailUserBinding? = null
    private val fragmentDetailUserBinding get() = _fragmentDetailUserBinding
    private val detailUserViewModel: DetailUserViewModel by viewModels()
    private lateinit var usernameUser: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentDetailUserBinding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return fragmentDetailUserBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameUser = DetailUserFragmentArgs.fromBundle(
            arguments as Bundle
        ).username

        detailUserViewModel.getDetailUser(usernameUser)

        setupObserve()
        setupUI()
    }

    private fun setupObserve() {
        detailUserViewModel.detailUser.observe(viewLifecycleOwner) { detailUser ->
            setDetailUserData(detailUser)
        }
        detailUserViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            setLoadingData(loading)
        }
    }

    private fun setLoadingData(loading: Boolean) {
        fragmentDetailUserBinding?.apply {
            if (loading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun setDetailUserData(detailUser: DetailUserResponse?) {
        fragmentDetailUserBinding?.apply {
            Glide.with(requireContext()).load(detailUser?.avatarUrl).into(detailsImgAvatar)
            detailsTvUsername.text = detailUser?.login
            detailsTvName.text = detailUser?.name
            detailsTvFollowing.text = "${detailUser?.following} following"
            detailsTvFollowers.text = "${detailUser?.followers} followers"
        }
    }

    private fun setupUI() {
        fragmentDetailUserBinding?.apply {
            val username = Bundle()
            username.putString(ARG_SECTION_USERNAME, usernameUser)
            val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity(), username)
            viewPager.adapter = sectionsPagerAdapter

            TabLayoutMediator(
                tabs,
                viewPager
            ) { tab, position ->
                tab.text = resources.getString(TAB_TITLES_DETAIL[position])
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentDetailUserBinding = null
    }
}