package com.example.expressgourmet.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expressgourmet.service.PersonaService
import com.example.expressgourmet.ui.theme.Orange

@Preview(showBackground = true)
@Composable
fun LoginScreen(
    onRecoverClick: () -> Unit = {},

    onRegistroClick: () -> Unit = {},
    onLoginSuccess:() -> Unit = {}
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Express",
                fontWeight = FontWeight.Black,
                fontSize = 45.sp
            )

            Text(
                text = "Gourmet",
                color = Color(0xFFE67E22),
                fontWeight = FontWeight.Black,
                fontSize = 45.sp
            )

            Spacer(modifier = Modifier.height(40.dp)) //ESPACIO ENTRE EL TEXTO Y EL CORREO

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    "Correo electrónico",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("ejemplo@correo.com") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Icono de correo"
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(35.dp)) //ESPACIO ENTRE EL CORREO Y LA CONTRASEÑA

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    "Contraseña",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("************") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Icono de contraseña"
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp)) //ESPACIO ENTRE LA CONTRASEÑA Y EL TEXTO DE RECUPERACIÓN

            Text(
                text = "¿Olvidaste tu contraseña?",
                color = Orange,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(vertical = 8.dp)
                    .clickable { onRecoverClick() },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(25.dp)) //ESPACIO ENTRE EL TEXTO DE RECUPERACIÓN Y EL BOTÓN


            Button(
                onClick = {
                    val usuario = PersonaService.iniciarSesion(email, password)
                    if (usuario != null) {
                        onLoginSuccess()
                    } else {
                        Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Orange)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "INICIAR SESIÓN",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold

                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿No tienes una cuenta?",
                    color = Color.Black,
                    fontSize = 18.sp
                )

                Spacer(Modifier.width(width = 10.dp))

                Text(
                    text = "Regístrate",
                    modifier = Modifier
                        .clickable { onRegistroClick() },
                    color = Orange,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}