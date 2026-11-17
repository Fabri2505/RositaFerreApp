package com.crisgaslac.proyecto_final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.crisgaslac.proyecto_final.presentation.common.AppScaffold
import com.crisgaslac.proyecto_final.presentation.navigation.AppNavigation
import com.crisgaslac.proyecto_final.ui.theme.Proyecto_finalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Proyecto_finalTheme {
                val navController = rememberNavController()
                AppScaffold(navController){paddingValues ->
                    Box(modifier = Modifier.fillMaxSize().padding(paddingValues)
                    ) {
                        AppNavigation(navController)
                    }
                }
            }
        }
    }
}
