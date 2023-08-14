package id.heycoding.submissiongithubuser.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.heycoding.submissiongithubuser.data.response.User
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
        setupUI()
    }

    private fun setupObserve() {
        searchUserViewModel.user.observe(viewLifecycleOwner) { searchUser ->
            setSearchUserData(searchUser)
        }

        searchUserViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            setLoadingData(loading)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setSearchUserData(userData: List<User>) {
        with(fragmentSearchUserBinding) {
            val listUserData = ArrayList<User>()
            for (user in userData) {
                listUserData.clear()
                listUserData.addAll(userData)
            }

            with(rvUser) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                val searchUserAdapter = SearchUserAdapter(this@SearchUserFragment, listUserData)
                adapter?.notifyDataSetChanged()
                adapter = searchUserAdapter
            }
        }
    }

    private fun setLoadingData(loading: Boolean) {
        fragmentSearchUserBinding.apply {
            if (loading) {
                progressbarUser.visibility = View.VISIBLE
            } else {
                progressbarUser.visibility = View.GONE
            }
        }
    }

    private fun setupUI() {
        fragmentSearchUserBinding.apply {
            searchviewUser.setupWithSearchBar(searchbarUser)
            searchviewUser.editText.setOnEditorActionListener { textView, actionId, event ->
                searchbarUser.text = searchviewUser.text
                searchviewUser.hide()
                searchUserViewModel.findSearchUser(searchviewUser.text.toString())
                false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentSearchUserBinding = null
    }

    override fun setDetailUser(user: User) {
        val dataUser = SearchUserFragmentDirections.actionSearchUserFragmentToDetailUserFragment()
        dataUser.username = user.login
        view?.findNavController()?.navigate(dataUser)
    }
}