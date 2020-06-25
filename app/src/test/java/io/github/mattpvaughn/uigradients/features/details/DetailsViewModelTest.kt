package io.github.mattpvaughn.uigradients.features.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.github.mattpvaughn.uigradients.data.local.getOrAwaitValue
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel
    private val fakeGradient: Gradient = Gradient("Leaning doctor", listOf("#FFF", "#000"))

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = DetailsViewModel(
            fakeGradient
        )
    }

    @Test
    fun getGradientLiveData() {
        assertEquals(viewModel.gradientLiveData.getOrAwaitValue(), fakeGradient)
    }
}
