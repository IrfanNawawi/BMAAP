package id.heycoding.submissiongithubuser.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import id.heycoding.submissiongithubuser.R
import id.heycoding.submissiongithubuser.data.local.entity.UserEntity
import id.heycoding.submissiongithubuser.data.remote.response.DetailUserResponse
import id.heycoding.submissiongithubuser.databinding.FragmentDetailUserBinding
import id.heycoding.submissiongithubuser.ui.ViewModelFactory
import id.heycoding.submissiongithubuser.util.Const.ARG_SECTION_USERLOGIN
import id.heycoding.submissiongithubuser.util.Const.TAB_TITLES_DETAIL

class DetailUserFragment : Fragment() {

    private var _fragmentDetailUserBinding: FragmentDetailUserBinding? = null
    private val fragmentDetailUserBinding get() = _fragmentDetailUserBinding

    private val detailUserViewModel by viewModels<DetailUserViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    private var buttonFavoriteState = false
    private lateinit var userIntent: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentDetailUserBinding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return fragmentDetailUserBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserve()
        setTabLayoutView()
    }

    private fun setupObserve() {
        detailUserViewModel.detailUser.observe(viewLifecycleOwner) { detailUser ->
            setDetailUserData(detailUser)

            /**
             * Function to check if user is favorited.
             */
            detailUserViewModel.checkFavoriteUserById(detailUser.id)
                .observe(viewLifecycleOwner) { favoriteUser ->
                    if (favoriteUser != null) {
                        buttonFavoriteState = true
                        fragmentDetailUserBinding?.fabFavoriteUser?.setImageResource(R.drawable.ic_favorite_active)
                    } else {
                        fragmentDetailUserBinding?.fabFavoriteUser?.setImageResource(R.drawable.ic_favorite_nonactive)
                    }
                }

            /**
             * Function to set Favorite User event.
             */
            fragmentDetailUserBinding?.fabFavoriteUser?.setOnClickListener {
                if (!buttonFavoriteState) {
                    buttonFavoriteState = true
                    detailUserViewModel.insertFavoriteUser(
                        UserEntity(
                            detailUser.id,
                            detailUser.login,
                            detailUser.avatarUrl
                        )
                    )
                } else {
                    buttonFavoriteState = false
                    detailUserViewModel.deleteFavoriteUser(detailUser.id)
                }
            }
        }

        detailUserViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            setLoadingData(loading)
        }
    }

    /**
     * Function to set loading data.
     */
    private fun setLoadingData(loading: Boolean) {
        fragmentDetailUserBinding?.apply {
            if (loading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    /**
     * Function to set the mapping data user.
     */
    private fun setDetailUserData(detailUser: DetailUserResponse) {
        fragmentDetailUserBinding?.apply {
            Glide.with(requireContext()).load(detailUser.avatarUrl).into(detailsImgAvatar)
            detailsTvUsername.text = detailUser.login
            detailsTvName.text = detailUser.name
            detailsTvFollowing.text = "${detailUser.following} following"
            detailsTvFollowers.text = "${detailUser.followers} followers"
        }
    }

    /**
     * Function to set the view of the tab layout.
     */
    private fun setTabLayoutView() {
        fragmentDetailUserBinding?.apply {
            userIntent = DetailUserFragmentArgs.fromBundle(
                arguments as Bundle
            ).login

            detailUserViewModel.getDetailUser(userIntent)
            val userLogin = Bundle()
            userLogin.putString(ARG_SECTION_USERLOGIN, userIntent)
            val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity(), userLogin)

            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(
                tabs,
                viewPager
            ) { tab, position ->
                tab.text = resources.getString(TAB_TITLES_DETAIL[position])
            }.attach()
        }
    }

    /**
     * Function to destroy fragment.
     */
    override fun onDestroy() {
        super.onDestroy()
        _fragmentDetailUserBinding = null
    }
}