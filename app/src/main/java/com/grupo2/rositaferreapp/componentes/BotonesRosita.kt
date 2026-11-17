package com.grupo2.rositaferreapp.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo2.rositaferreapp.ui.theme.AzulSecundario
import com.grupo2.rositaferreapp.ui.theme.ContenedorAlto
import com.grupo2.rositaferreapp.ui.theme.NaranjaPrincipal

// Clase que define la configuración visual de un botón
class EstiloBoton(
    val colorFondo: Color,
    val colorTexto: Color,
    val alturaDp: Int = 52
)

@Composable
fun BotonPrincipal(
    texto: String,
    icono: ImageVector? = null,
    habilitado: Boolean = true,
    estilo: EstiloBoton = EstiloBoton(NaranjaPrincipal, Color.White),
    alHacerClick: () -> Unit
) {
    Button(
        onClick = alHacerClick,
        enabled = habilitado,
        colors = ButtonDefaults.buttonColors(
            containerColor = estilo.colorFondo,
            contentColor = estilo.colorTexto
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(estilo.alturaDp.dp)
    ) {
        if (icono != null) {
            Icon(imageVector = icono, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = texto,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BotonSecundario(
    texto: String,
    icono: ImageVector? = null,
    alHacerClick: () -> Unit
) {
    val estiloSecundario = EstiloBoton(
        colorFondo = ContenedorAlto,
        colorTexto = AzulSecundario
    )
    BotonPrincipal(
        texto = texto,
        icono = icono,
        estilo = estiloSecundario,
        alHacerClick = alHacerClick
    )
}