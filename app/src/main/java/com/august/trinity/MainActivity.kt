package com.august.trinity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.august.trinity.ui.theme.TrinityTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val packageInstaller = packageManager.getInstallerPackageName(packageName)

        setContent {
            TrinityTheme {
                Column {
                    Text(text = "packageInstaller : $packageInstaller")
                    Text(text = "packageManager : $packageManager")
                    Text(text = "packageManager.packageInstaller : ${packageManager.packageInstaller.allSessions}")
                    Text(text = "packageName: $packageName")
                    Text(text = "packageResourcePath: $packageResourcePath")
                    Text(text="${packageManager.getPackageInfo(packageName, 0)}")
                }
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