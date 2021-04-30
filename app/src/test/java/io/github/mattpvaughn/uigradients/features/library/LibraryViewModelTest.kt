package io.github.mattpvaughn.uigradients.features.library

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.github.mattpvaughn.uigradients.data.local.FakeGradientRepository
import io.github.mattpvaughn.uigradients.data.local.IGradientRepo
import io.github.mattpvaughn.uigradients.data.local.awaitValueMatching
import io.github.mattpvaughn.uigradients.data.local.getOrAwaitValue
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
class LibraryViewModelTest {

    private lateinit var successfulRepository: IGradientRepo
    private lateinit var failureRepository: IGradientRepo

    // Needed so the app will handle [LiveData]s off of the main thread
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        successfulRepository = spyk(FakeGradientRepository(throwsError = false))
        failureRepository = spyk(FakeGradientRepository(throwsError = true))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `test viewstate emits correctly for a non-empty repository`() {
        val viewModel = LibraryViewModel(successfulRepository)
        viewModel.load()

        // Await a state indicating loading has finished
        val viewStateLoaded = viewModel.viewState.awaitValueMatching {
            it is LibraryViewModel.ViewState.Error || it is LibraryViewModel.ViewState.GradientList
        }

        assertThat(
            viewStateLoaded, `is`(instanceOf(LibraryViewModel.ViewState.GradientList::class.java))
        )
        assertThat(
            (viewStateLoaded as LibraryViewModel.ViewState.GradientList), `is`(
                equalTo(
                    LibraryViewModel.ViewState.GradientList(
                        (successfulRepository as FakeGradientRepository).fakeGradientsLiveData.getOrAwaitValue()
                    )
                )
            )
        )
    }

    @Test
    fun `test default viewstate is 'no items found' if repo isn't loaded`() {
        val viewModel = LibraryViewModel(successfulRepository)
        val viewState = viewModel.viewState.getOrAwaitValue()
        assertThat(viewState, `is`(instanceOf(LibraryViewModel.ViewState.NoItemsFound::class.java)))
    }

    @Test
    fun `test load() function calls updateData() in the repo`() {
        val viewModel = LibraryViewModel(successfulRepository)
        viewModel.load()

        // Ensure load forces refresh in repo
        coVerify { successfulRepository.updateData() }
    }

    @Test
    fun `test repository failure emits a failure UI state`() {
        val viewModel = LibraryViewModel(failureRepository)
        viewModel.load()

        // Ensure error message is posted
        val viewStateLoaded = viewModel.viewState.awaitValueMatching {
            it is LibraryViewModel.ViewState.Error || it is LibraryViewModel.ViewState.GradientList
        }
        assertThat(
            viewModel.errorMessage.getOrAwaitValue().peekContent(),
            `is`(instanceOf(String::class.java))
        )
        assertThat(
            viewStateLoaded, `is`(instanceOf(LibraryViewModel.ViewState.Error::class.java))
        )

    }

    @Test
    fun `test repository failure emits failure message`() {
        val viewModel = LibraryViewModel(failureRepository)

        viewModel.load()

        // Wait for [LibraryViewModel] to post a value to [errorMessage]
        val errorMessage = viewModel.errorMessage.awaitValueMatching {
            it?.peekContent() is String
        }.peekContent()
        assertThat(errorMessage, `is`(instanceOf(String::class.java)))
    }

    @Test
    fun `test successful repository does not emit failure message`() {
        val viewModel = LibraryViewModel(successfulRepository)
        viewModel.load()

        try {
            viewModel.errorMessage.awaitValueMatching { it?.peekContent() is String }
            fail("ViewModel emitted error message when it should not have")
        } catch (t: TimeoutException) {
            // succeed
        }
    }


}