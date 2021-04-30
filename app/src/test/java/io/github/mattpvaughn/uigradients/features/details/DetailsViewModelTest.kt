package io.github.mattpvaughn.uigradients.features.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.github.mattpvaughn.uigradients.data.local.getOrAwaitValue
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailsViewModel

    private val exampleGradient = Gradient(
        name = "Omolon", colors = listOf("#FFFFFA", "#00000A")
    )

    @Before
    fun setUp() {
        viewModel = DetailsViewModel(exampleGradient)
    }

    @Test
    fun `test livedata exposes correct gradient`() {
        assertEquals(viewModel.gradientLiveData.getOrAwaitValue(), exampleGradient)
    }
}
