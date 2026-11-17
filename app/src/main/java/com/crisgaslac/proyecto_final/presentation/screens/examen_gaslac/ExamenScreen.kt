package com.crisgaslac.proyecto_final.presentation.screens.examen_gaslac
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crisgaslac.proyecto_final.R
import com.crisgaslac.proyecto_final.presentation.common.EtiquetaTecnica
import com.crisgaslac.proyecto_final.presentation.common.TarjetaEstadistica

@Composable
fun ExamenScreen() {
    val colorFondo = Color(0xFFE0E0E0)
    val colorAzulOscuro = Color(0xFF070954)
    val colorAzulMedio = Color(0xFF1F0A8D)
    val colorGrisOscuro = Color(0xFF353B41)
    val colorVerdeDot = Color(0xFF00A86B)
    val colorBotonCV = Color(0xFF150756)
    val colorBotonContactarBorder = Color(0xFF6D6D73)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // 1. Foto de perfil
        Box(
            modifier = Modifier
                .size(115.dp)
                .clip(CircleShape)
                .border(2.dp, colorBotonContactarBorder, CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.perfil),
                contentDescription = "Foto de Perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Nombre y Título
        Text(
            text = "Jorge Narvaez",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = colorAzulOscuro
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Ingeniero de Sistemas",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = colorGrisOscuro
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Pill Bar
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFFA19D9D),
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(colorVerdeDot, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Full-time",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorAzulMedio
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(colorVerdeDot, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Lima, Perú",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorAzulMedio
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de descargas y contacto
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = colorBotonCV),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(42.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FileDownload,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Descargar CV",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                border = BorderStroke(1.dp, colorBotonContactarBorder),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(42.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Mail,
                    contentDescription = null,
                    tint = colorAzulMedio,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Contactar",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorAzulMedio
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Subtítulo de Perfil Profesional y Resumen
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Perfil Profesional",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = colorAzulOscuro
            )
            Text(
                text = "RESUMEN",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = colorGrisOscuro
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Card de la descripción del perfil
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFEAEAF5)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Ingeniero de Sistemas con sólida experiencia liderando la arquitectura e ingeniería de plataformas empresariales de alta concurrencia. Especializado en diseño de microservicios resilientes, computación en la nube (AWS) y transformación digital. Enfocado en alinear visión técnica con objetivos estratégicos de negocio.",
                fontSize = 15.sp,
                color = colorGrisOscuro,
                lineHeight = 18.sp,
                modifier = Modifier.padding(14.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Componentes comunes: TarjetaEstadistica
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TarjetaEstadistica(
                number = "8+",
                label = "Años de Exp.",
                modifier = Modifier.weight(1f)
            )
            TarjetaEstadistica(
                number = "25+",
                label = "Proyectos",
                modifier = Modifier.weight(1f)
            )
            TarjetaEstadistica(
                number = "99.9%",
                label = "Disponibilidad",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Subtítulo Habilidades Técnicas
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Habilidades Técnicas",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = colorAzulOscuro
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Componentes comunes: EtiquetaTecnica
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFEAEAF5)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Feedback,
                        contentDescription = null,
                        tint = colorAzulOscuro,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Lenguajes & Datos",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF101B35)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    EtiquetaTecnica(text = "Kotlin", modifier = Modifier.weight(1f))
                    EtiquetaTecnica(text = "Python", modifier = Modifier.weight(1f))
                    EtiquetaTecnica(text = "TypeScript", modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    EtiquetaTecnica(text = "AWS", modifier = Modifier.weight(1f))
                    EtiquetaTecnica(text = "Java", modifier = Modifier.weight(1f))
                    EtiquetaTecnica(text = "C#", modifier = Modifier.weight(1f))
                }
            }
        }
    }
}