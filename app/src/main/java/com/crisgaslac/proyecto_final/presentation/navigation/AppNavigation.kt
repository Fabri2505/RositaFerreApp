package com.crisgaslac.proyecto_final.presentation.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.crisgaslac.proyecto_final.presentation.screens.buscar.BuscarScreen
import com.crisgaslac.proyecto_final.presentation.screens.detalle.DetalleScreen
import com.crisgaslac.proyecto_final.presentation.screens.formulario.FormularioScreen
import com.crisgaslac.proyecto_final.presentation.screens.inicio.InicioScreen

@Composable
fun AppNavigation(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Inicio.ruta,
        modifier = Modifier
    ){
        composable(BottomNavItem.Inicio.ruta){
            InicioScreen(
                onDetalleLibro = {id ->
                    navController.navigate(BottomNavItem.Detalle.ruta)
                }
            )
        }

        composable(route = BottomNavItem.Detalle.ruta) {
            DetalleScreen()
        }

        composable(route = BottomNavItem.Form.ruta) {
            FormularioScreen()
        }

        composable(BottomNavItem.Buscar.ruta){
            BuscarScreen()

        }
    }
}
