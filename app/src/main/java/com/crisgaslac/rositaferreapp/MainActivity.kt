package com.crisgaslac.rositaferreapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.crisgaslac.rositaferreapp.ui.theme.RositaFerreAppTheme
import com.crisgaslac.rositaferreapp.ui.theme.pantallas.PantallaBienvenida
import com.crisgaslac.rositaferreapp.ui.theme.pantallas.PantallaLogin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RositaFerreAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var pantallaActual by remember { mutableStateOf("bienvenida") }
                    val contexto = LocalContext.current

                    when (pantallaActual) {
                        "bienvenida" -> {
                            PantallaBienvenida(
                                alPresionarExplorarCatalogo = {
                                    Toast.makeText(contexto, "Abriendo Catálogo...", Toast.LENGTH_SHORT).show()
                                },
                                alPresionarIniciarSesion = {
                                    pantallaActual = "login"
                                }
                            )
                        }
                        "login" -> {
                            PantallaLogin(
                                alVolver = {
                                    pantallaActual = "bienvenida"
                                },
                                alIniciarSesionExitoso = { doc ->
                                    Toast.makeText(contexto, "Sesión iniciada: $doc", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}