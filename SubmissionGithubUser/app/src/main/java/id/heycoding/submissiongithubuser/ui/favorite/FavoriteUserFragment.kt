package id.heycoding.submissiongithubuser.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.heycoding.submissiongithubuser.data.local.entity.UserEntity
import id.heycoding.submissiongithubuser.databinding.FragmentFavoriteUserBinding
import id.heycoding.submissiongithubuser.ui.ViewModelFactory

class FavoriteUserFragment : Fragment(), FavoriteUserCallback {

    private var _fragmentFavoriteUserBinding: FragmentFavoriteUserBinding? = null
    private val fragmentFavoriteUserBinding get() = _fragmentFavoriteUserBinding!!

    private val favoriteUserViewModel by viewModels<FavoriteUserViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentFavoriteUserBinding =
            FragmentFavoriteUserBinding.inflate(inflater, container, false)
        return fragmentFavoriteUserBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserve()
    }

    /**
     * Function to set observe live data.
     */
    private fun setupObserve() {
        favoriteUserViewModel.favoriteUser().observe(viewLifecycleOwner) { favoriteUser ->
            val listFavoriteUserData = ArrayList<UserEntity>()
            for (favorite in favoriteUser) {
                listFavoriteUserData.clear()
                listFavoriteUserData.addAll(favoriteUser)
            }

            with(fragmentFavoriteUserBinding.rvFavoriteUser) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                val favoriteUserAdapter =
                    FavoriteUserAdapter(this@FavoriteUserFragment, listFavoriteUserData)
                adapter?.notifyDataSetChanged()
                adapter = favoriteUserAdapter
            }
        }
    }

    /**
     * Function to set intent passing data user.
     */
    override fun setIntentDetailUser(userEntity: UserEntity) {
        val dataUser =
            FavoriteUserFragmentDirections.actionFavoriteUserFragmentToDetailUserFragment()
        dataUser.login = userEntity.username!!
        view?.findNavController()?.navigate(dataUser)
    }

    /**
     * Function to destroy fragment.
     */
    override fun onDestroy() {
        super.onDestroy()
        _fragmentFavoriteUserBinding = null
    }
}