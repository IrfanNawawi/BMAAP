package id.heycoding.submissiongithubuser.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.heycoding.submissiongithubuser.R
import id.heycoding.submissiongithubuser.data.remote.response.User
import id.heycoding.submissiongithubuser.databinding.FragmentSearchUserBinding


class SearchUserFragment : Fragment(), SearchUserCallback {

    private var _fragmentSearchUserBinding: FragmentSearchUserBinding? = null
    private val fragmentSearchUserBinding get() = _fragmentSearchUserBinding!!

    private val searchUserViewModel: SearchUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentSearchUserBinding = FragmentSearchUserBinding.inflate(inflater, container, false)
        return fragmentSearchUserBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserve()
        setLayoutView()
    }

    /**
     * Function to set observe live data.
     */
    private fun setupObserve() {
        searchUserViewModel.user.observe(viewLifecycleOwner) { searchUser ->
            val listUserData = ArrayList<User>()
            for (user in searchUser) {
                listUserData.clear()
                listUserData.addAll(searchUser)
            }

            with(fragmentSearchUserBinding.rvUser) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                val searchUserAdapter = SearchUserAdapter(this@SearchUserFragment, listUserData)
                adapter?.notifyDataSetChanged()
                adapter = searchUserAdapter
            }
        }

        searchUserViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            setLoadingData(loading)
        }
    }

    /**
     * Function to set loading data.
     */
    private fun setLoadingData(loading: Boolean) {
        fragmentSearchUserBinding.apply {
            if (loading) {
                progressbarUser.visibility = View.VISIBLE
            } else {
                progressbarUser.visibility = View.GONE
            }
        }
    }

    /**
     * Function to set the view of the layout.
     */
    private fun setLayoutView() {
        searchUserViewModel.searchUser("a")
        fragmentSearchUserBinding.apply {
            searchviewUser.setupWithSearchBar(searchbarUser)
            searchviewUser.editText.setOnEditorActionListener { _, _, _ ->
                searchbarUser.text = searchviewUser.text
                searchviewUser.hide()
                searchUserViewModel.searchUser(searchviewUser.text.toString())
                false
            }

            /**
             * Function when item on menu is clicked.
             */
            searchbarUser.setOnMenuItemClickListener { menuItem ->
                // Handle menuItem click.
                when (menuItem.itemId) {
                    R.id.menu_favorite_user -> {
                        view?.findNavController()
                            ?.navigate(R.id.action_searchUserFragment_to_favoriteUserFragment)
                        true
                    }
                    R.id.menu_darkmode -> {
                        view?.findNavController()
                            ?.navigate(R.id.action_searchUserFragment_to_settingsFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    /**
     * Function to set intent passing data user.
     */
    override fun setIntentDetailUser(user: User) {
        val dataUser = SearchUserFragmentDirections.actionSearchUserFragmentToDetailUserFragment()
        dataUser.login = user.login
        view?.findNavController()?.navigate(dataUser)
    }

    /**
     * Function to destroy fragment.
     */
    override fun onDestroy() {
        super.onDestroy()
        _fragmentSearchUserBinding = null
    }
}