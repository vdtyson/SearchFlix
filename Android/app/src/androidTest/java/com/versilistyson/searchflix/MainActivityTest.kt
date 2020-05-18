package com.versilistyson.searchflix

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.versilistyson.searchflix.presentation.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule val scenario = launch(MainActivity::class.java)

    @Test
    fun bottomNavBarTest() {
        TODO()
    }
}