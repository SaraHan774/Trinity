package com.august.trinity.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * https://medium.com/@theAndroidDeveloper/yet-another-pitfall-in-jetpack-compose-you-must-be-aware-of-225a1d07d033
 */
class TestViewModel: ViewModel() {
    var currentCounterValue by mutableIntStateOf(0)
        private set

    fun incrementCounter() {
        currentCounterValue++
    }
}