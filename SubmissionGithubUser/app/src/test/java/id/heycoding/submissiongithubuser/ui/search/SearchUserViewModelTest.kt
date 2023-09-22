package id.heycoding.submissiongithubuser.ui.search

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class SearchUserViewModelTest {

    private lateinit var viewModel: SearchUserViewModel

    @Before
    fun setUp() {
        viewModel = SearchUserViewModel()
    }

    @Test
    fun getUser() {
        val movieEntities = viewModel.getUser()
        assertNotNull(movieEntities)
        assertEquals(3, movieEntities.size)
    }
}