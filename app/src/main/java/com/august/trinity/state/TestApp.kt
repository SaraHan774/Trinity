package com.august.trinity.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.august.trinity.interop.DropdownDemo
import com.august.trinity.interop.MyDropdownMenu

/**
 * https://medium.com/@theAndroidDeveloper/yet-another-pitfall-in-jetpack-compose-you-must-be-aware-of-225a1d07d033
 */
@Composable
fun TestApp(viewModel: TestViewModel = viewModel()) {
    val counterValue = viewModel.currentCounterValue // state read from viewModel
    val isDivisible by remember {
        derivedStateOf { counterValue % 10 == 0 }
    } // 10 으로 나눌 수 있나 ?

    Column {
//        Text(text = "Counter: $counterValue")
//        Button(onClick = { viewModel.incrementCounter() }) {
//            Text(text = "Increment")
//        }
        // recompose scope
        // part of the UI tree that will get re-composed
//        Text(text = "Divisible by 10: $isDivisible")

        Button(onClick = { viewModel.sortList() }) {
            Text(text = "Sort")
        }
    }
}

@Composable
fun TestApp2(modifier: Modifier = Modifier) {
    val state1 by remember {
        mutableIntStateOf(0)
    }
    val state2 by remember {
        mutableIntStateOf(0)
    }

    Column {
        Row {
            // recomposition scope of state 1
            // nearest composable is the first row composable
            Text(text = "State 1: $state1")
        }
        Row {
            // state 2 is being read here.
            // nearest composable is the second row composable
            Text(text = "State 2: $state2")
        }
    }
}

@Composable
fun TestApp3(viewModel: TestViewModel = viewModel<TestViewModel>()) {
    val counterValue = viewModel.currentCounterValue // state read
    // using remember with keys
    val isDivisible by remember(counterValue) {
        derivedStateOf { counterValue % 10 == 0 }
    }
    Column {
        Text(text = "Counter: $counterValue")
        Button(onClick = { viewModel.incrementCounter() }) {
            Text(text = "Increment")
        }
        Text(text = "Divisible by 10: $isDivisible")
    }

    LaunchedEffect(key1 = Unit) {
        try {
            viewModel.testAsync()
        } catch (e: Exception) {
            Log.d("===", "error~!")
        }
    }
}

@Composable
fun TestApp4(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DropdownDemo()
    }
}
