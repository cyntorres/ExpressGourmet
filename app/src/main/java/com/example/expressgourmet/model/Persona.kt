package com.example.expressgourmet.model

data class Persona(
    //Val significa que es inmutable y Var mutable
    val id: String = "",
    val nombre: String = "",
    val correo: String = "",
    val contraseña: String = ""
)