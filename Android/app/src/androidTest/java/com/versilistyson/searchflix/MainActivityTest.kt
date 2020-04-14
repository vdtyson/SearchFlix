package com.versilistyson.searchflix

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.versilistyson.searchflix.di.SearchFlixApp
import com.versilistyson.searchflix.presentation.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule val scenario = launch(MainActivity::class.java)

    @Test
    fun bottomNavBarTest() {
        TODO()
    }
}