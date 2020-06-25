package io.github.mattpvaughn.uigradients.features.library

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.mattpvaughn.uigradients.R
import io.github.mattpvaughn.uigradients.application.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LibraryFragmentTest {

    @Before
    fun setupActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testOpenGradientDetailsFragment() {
        onView(withId(R.id.library_list)).perform(
            actionOnItemAtPosition<GradientAdapter.GradientViewHolder>(0, click())
        )

        onView(withId(R.id.fragment_details)).check(matches(isDisplayed()))
    }
}

