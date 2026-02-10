package com.example.expressgourmet.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expressgourmet.R

data class Receta(
    val nombre: String,
    val tiempo: String,
    val dificultad: String,
    val calificacion: String,
    val imagenRes: Int
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUserScreen(
    darkTheme: Boolean,
    onToggleDarkTheme: () -> Unit,
    onScaleFont: (Float) -> Unit
) {
    val recetas = listOf(
        Receta("Ensalada de pollo", "25 min", "Media", "4.9", R.drawable.home), // Cambia por tus fotos
        Receta("Gohan de salmón", "35 min", "Alta", "5.0", R.drawable.fondo)
    )

    val categorias = listOf("Todo", "Italiana", "Postres", "Francesa", "Mexicana")

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Express",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF2D2D2D)
                            )
                        )
                        Text(
                            text = "Gourmet",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFE67E22) // Naranja Gourmet
                            )
                        )
                    }

                    // BOTONES DE ACCESIBILIDAD
                    IconButton(onClick = { onScaleFont(0f) }) {
                        Icon(
                            painter = painterResource(id = R.mipmap.tamano_letra),
                            contentDescription = "Cambiar tamaño de letra",
                            tint = if (darkTheme) Color.White else Color.Black
                        )
                    }

                    IconButton(onClick = onToggleDarkTheme) {
                        Icon(
                            painter = if (darkTheme)
                                painterResource(id = R.mipmap.modo_oscuro)
                            else
                                painterResource(id = R.mipmap.modo_oscuro),
                            contentDescription = "Cambiar tema",
                            tint = if (darkTheme) Color.White else Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // BUSCAR
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Buscar recetas gourmet...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // FILTROS
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categorias) { cat ->
                        FilterChip(
                            selected = cat == "Todo",
                            onClick = { },
                            label = { Text(cat) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFE67E22),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Recetas Destacadas",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = { }) {
                        Text("Ver todas", color = Color(0xFFE67E22))
                    }
                }
            }

            // TARJETAS
            items(recetas) { receta ->
                Card(
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Box {
                            Image(
                                painter = painterResource(id = receta.imagenRes),
                                contentDescription = receta.nombre,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentScale = ContentScale.Crop
                            )

                            IconButton(
                                onClick = { },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .background(Color.White.copy(alpha = 0.5f), CircleShape)
                            ) {
                                Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                            }
                        }

                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    receta.nombre,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(16.dp))
                                Text(receta.calificacion, style = MaterialTheme.typography.bodySmall)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.DateRange, null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                                    Text(" ${receta.tiempo}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                                    Text(" ${receta.dificultad}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeUserScreenPreview() {
    com.example.expressgourmet.ui.theme.ExpressGourmetTheme {
        HomeUserScreen(
            darkTheme = false,
            onToggleDarkTheme = {},
            onScaleFont = {}
        )
    }
}