package com.august.trinity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.august.trinity.interop.InteropActivity
import com.august.trinity.state.TestApp4
import com.august.trinity.test.TestApp5
import com.august.trinity.ui.theme.TrinityTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrinityTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        Home(
                            navigateToDerivedStateTest = {
                                navController.navigate("composeTest")
                            },
                            navigateInteropTest = {
                                startActivity(Intent(this@MainActivity, InteropActivity::class.java))
                            },
                            navigateComposeTest1 = {
                                navController.navigate("composeTest1")
                            }
                        )
                    }

                    composable("composeTest") {
                        TestApp4()
                    }
                    composable("composeTest1") {
                        TestApp5()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navigateToDerivedStateTest: () -> Unit = {},
    navigateInteropTest: () -> Unit = {},
    navigateComposeTest1: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = { Text(text = "Trinity") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState, true)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = navigateToDerivedStateTest) {
                Text(text = "Test")
            }
            Button(onClick = navigateInteropTest) {
                Text(text = "Interop Test")
            }
            Button(onClick = navigateComposeTest1) {
                Text(text = "Interop Test")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TrinityTheme {

    }
}
// derivedStateOf
// property delegate for compose state objects