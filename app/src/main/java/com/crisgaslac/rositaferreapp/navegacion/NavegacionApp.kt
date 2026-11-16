package com.crisgaslac.rositaferreapp.navegacion

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.crisgaslac.rositaferreapp.ui.theme.pantallas.PantallaBienvenida
import com.crisgaslac.rositaferreapp.ui.theme.pantallas.PantallaLogin

@Composable
fun NavegacionApp() {
    var pantallaActual by remember { mutableStateOf("bienvenida") }
    val contexto = LocalContext.current

    when (pantallaActual) {
        "bienvenida" -> {
            PantallaBienvenida(
                alPresionarExplorarCatalogo = {
                    Toast.makeText(contexto, "Próximamente: Pantalla 3 (Catálogo)", Toast.LENGTH_SHORT).show()
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
                alIniciarSesionExitoso = { documento ->
                    Toast.makeText(contexto, "Bienvenido usuario: $documento", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}