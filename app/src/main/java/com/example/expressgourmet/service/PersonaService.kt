package com.example.expressgourmet.service

import com.example.expressgourmet.model.Persona

object PersonaService {

    private var usuarioLogueado: Persona? = null

    private val personas = mutableListOf<Persona>()

    fun agregarPersona(persona: Persona){
        personas.add(persona)
    }

    fun obtenerPersonas(): List<Persona>{
        return personas.toList()
    }

    fun obtenerPersonaPorId(id: String): Persona?{
        return personas.find { it.id == id }
    }

    //El signo de pregunta es para decir que puede ser nulo
    fun iniciarSesion(correo: String, contraseña: String): Persona?{
        val persona = personas.find { it.correo == correo && it.contraseña == contraseña }
        usuarioLogueado = persona

        return persona
    }

    fun obtenerUsuarioActual(): Persona?{
        return usuarioLogueado
    }



}