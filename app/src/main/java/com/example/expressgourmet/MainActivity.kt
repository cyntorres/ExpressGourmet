package com.example.expressgourmet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.expressgourmet.ui.navigation.NavGraph
import com.example.expressgourmet.ui.theme.ExpressGourmetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkTheme by remember { mutableStateOf(false) }
            var fontScale by remember { mutableStateOf(1.0f) }

            ExpressGourmetTheme(darkTheme = darkTheme, fontScale = fontScale) {
                val navController = rememberNavController()

                NavGraph(
                    navController = navController,
                    darkTheme = darkTheme,
                    onToggleDarkTheme = { darkTheme = !darkTheme },
                    onScaleFont = {
                        fontScale = when (fontScale) {
                            1.0f -> 1.4f
                            1.4f -> 1.8f
                            else -> 1.0f
                        }
                    }
                )
            }
        }
    }
}