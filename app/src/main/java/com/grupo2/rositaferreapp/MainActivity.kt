package com.grupo2.rositaferreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.grupo2.rositaferreapp.navegacion.ControladorNavegacion
import com.grupo2.rositaferreapp.navegacion.RutaPantalla
import com.grupo2.rositaferreapp.ui.pantallas.PantallaAdminPedidos
import com.grupo2.rositaferreapp.ui.pantallas.PantallaBienvenida
import com.grupo2.rositaferreapp.ui.pantallas.PantallaCarrito
import com.grupo2.rositaferreapp.ui.pantallas.PantallaCatalogo
import com.grupo2.rositaferreapp.ui.pantallas.PantallaDetalleProducto
import com.grupo2.rositaferreapp.ui.pantallas.PantallaEntrega
import com.grupo2.rositaferreapp.ui.pantallas.PantallaEstadoPedido
import com.grupo2.rositaferreapp.ui.pantallas.PantallaLogin
import com.grupo2.rositaferreapp.ui.pantallas.PantallaPago
import com.grupo2.rositaferreapp.ui.pantallas.PantallaPagoExitoso
import com.grupo2.rositaferreapp.ui.pantallas.PantallaPedidos
import com.grupo2.rositaferreapp.ui.pantallas.PantallaReclamo
import com.grupo2.rositaferreapp.ui.pantallas.PantallaRegistro
import com.grupo2.rositaferreapp.ui.theme.RositaFerreAppTheme
import com.grupo2.rositaferreapp.ui.pantallas.PantallaLoginAdmin
import com.grupo2.rositaferreapp.ui.pantallas.PantallaAdminProductos
import com.grupo2.rositaferreapp.ui.pantallas.PantallaPanelAdmin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RositaFerreAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = remember { ControladorNavegacion() }

                    when (navController.pantallaActual) {
                        RutaPantalla.BIENVENIDA -> PantallaBienvenida(navController)
                        RutaPantalla.LOGIN -> PantallaLogin(navController)
                        RutaPantalla.LOGIN_ADMIN -> PantallaLoginAdmin(navController)
                        RutaPantalla.PANEL_ADMIN -> PantallaPanelAdmin(navController)
                        RutaPantalla.ADMIN_PRODUCTOS -> PantallaAdminProductos(navController)
                        RutaPantalla.REGISTRO -> PantallaRegistro(navController)
                        RutaPantalla.CATALOGO -> PantallaCatalogo(navController)
                        RutaPantalla.DETALLE -> PantallaDetalleProducto(navController)
                        RutaPantalla.CARRITO -> PantallaCarrito(navController)
                        RutaPantalla.ENTREGA -> PantallaEntrega(navController)
                        RutaPantalla.PAGO -> PantallaPago(navController)
                        RutaPantalla.PAGO_EXITOSO -> PantallaPagoExitoso(navController)
                        RutaPantalla.PEDIDOS -> PantallaPedidos(navController)
                        RutaPantalla.ESTADO_PEDIDO -> PantallaEstadoPedido(navController)
                        RutaPantalla.RECLAMO -> PantallaReclamo(navController)
                        RutaPantalla.ADMIN_PEDIDOS -> PantallaAdminPedidos(navController)
                    }
                }
            }
        }
    }
}