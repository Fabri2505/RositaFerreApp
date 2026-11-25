package com.grupo2.rositaferreapp.datos

import android.content.Context

object GestorUsuarios {
    private const val ARCHIVO_PREFS = "rosita_usuarios"

    // Función 1: Valida credenciales básicas por defecto o usuarios registrados
    fun iniciarSesion(context: Context, documento: String, clave: String): String? {
        // Cuenta Administrador
        if (documento == "admin" && clave == "admin123") {
            return "ADMINISTRADOR"
        }

        // Credenciales básicas listas para pruebas
        if (documento == "12345678" && clave == "1234") {
            return "Bienvenido Jose Luis Paucar"
        }
        if (documento == "20601234567" && clave == "1234") {
            return "Corporación Ferretería Rosita S.A.C."
        }

        // Busca si el usuario se registró en el celular
        val prefs = context.getSharedPreferences(ARCHIVO_PREFS, Context.MODE_PRIVATE)
        val claveGuardada = prefs.getString("clave_$documento", null)

        return if (claveGuardada != null && claveGuardada == clave) {
            prefs.getString("nombre_$documento", documento)
        } else {
            null
        }
    }

    // Función 2: Guarda un usuario nuevo desde la pantalla de registro
    fun registrarUsuario(context: Context, usuario: Usuario) {
        val prefs = context.getSharedPreferences(ARCHIVO_PREFS, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString("clave_${usuario.documento}", usuario.clave)
            putString("nombre_${usuario.documento}", usuario.nombre)
            putString("tipo_${usuario.documento}", usuario.tipo.name)
            apply()
        }
    }

    // Función 3: Valida si la longitud del documento es correcta (8 para DNI, 11 para RUC o "admin")
    fun esDocumentoValido(documento: String, tipo: TipoCliente): Boolean {
        if (documento == "admin") return true
        return if (tipo == TipoCliente.PERSONA) {
            documento.length == 8
        } else {
            documento.length == 11
        }
    }
}