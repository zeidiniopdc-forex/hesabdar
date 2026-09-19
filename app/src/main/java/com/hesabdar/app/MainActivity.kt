package com.hesabdar.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.hesabdar.app.ui.navigation.HesabdarApp
import com.hesabdar.app.ui.theme.HesabdarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HesabdarTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HesabdarApp()
                }
            }
        }
    }
}
