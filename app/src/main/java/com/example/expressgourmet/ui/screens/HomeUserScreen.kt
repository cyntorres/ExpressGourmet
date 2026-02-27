package com.example.expressgourmet.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expressgourmet.R

data class Receta(
    val id: Int, // Añadido ID para identificar cuál borrar
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
    val context = LocalContext.current

    // 1. ESTADOS PARA FILTROS
    var searchQuery by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf("Todo") }

    // 2. ESTADO DE LA LISTA (DATOS DINÁMICOS)
    val listaRecetas = remember {
        mutableStateListOf(
            Receta(1, "Ensalada de pollo", "25 min", "Media", "4.9", R.drawable.home),
            Receta(2, "Gohan de salmón", "35 min", "Alta", "5.0", R.drawable.fondo)
        )
    }

    // 3. LÓGICA DE FILTRADO (Se aplica antes de mostrar la lista)
    val recetasFiltradas = listaRecetas.filter { receta ->
        val matchesSearch = receta.nombre.contains(searchQuery, ignoreCase = true)
        // Como tu clase Receta actual no tiene campo 'categoria', filtramos por nombre por ahora
        // o puedes añadir el campo a la data class para un filtro más preciso.
        matchesSearch
    }

    val categorias = listOf("Todo", "Italiana", "Postres", "Francesa")

    // ESTADOS PARA EL FORMULARIO (MODAL)
    var showDialog by remember { mutableStateOf(false) }
    var nombreInput by remember { mutableStateOf("") }
    var tiempoInput by remember { mutableStateOf("") }
    var recetaAEditar by remember { mutableStateOf<Receta?>(null) }

    // --- DIÁLOGO DINÁMICO (Sin cambios en tu lógica) ---
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false; recetaAEditar = null },
            title = { Text(if (recetaAEditar == null) "Nueva Receta" else "Editar Receta", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = nombreInput,
                        onValueChange = { nombreInput = it },
                        label = { Text("Nombre del plato") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = tiempoInput,
                        onValueChange = { tiempoInput = it },
                        label = { Text("Tiempo (ej: 25 min)") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE67E22)),
                    onClick = {
                        if (nombreInput.isNotBlank()) {
                            if (recetaAEditar == null) {
                                val nuevoId = (listaRecetas.maxOfOrNull { it.id } ?: 0) + 1
                                listaRecetas.add(Receta(nuevoId, nombreInput, tiempoInput, "Media", "0.0", R.drawable.home))
                            } else {
                                val index = listaRecetas.indexOfFirst { it.id == recetaAEditar!!.id }
                                if (index != -1) {
                                    listaRecetas[index] = recetaAEditar!!.copy(nombre = nombreInput, tiempo = tiempoInput)
                                }
                            }
                            nombreInput = ""; tiempoInput = ""; recetaAEditar = null; showDialog = false
                        }
                    }
                ) { Text("Guardar", color = Color.White) }
            },
            dismissButton = { TextButton(onClick = { showDialog = false; recetaAEditar = null }) { Text("Cancelar") } }
        )
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                // Header (Express Gourmet) y botones de accesibilidad aquí...

                Spacer(modifier = Modifier.height(16.dp))

                // BUSCADOR CONECTADO AL FILTRO
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar recetas gourmet...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { nombreInput = ""; tiempoInput = ""; recetaAEditar = null; showDialog = true },
                containerColor = Color(0xFFE67E22),
                contentColor = Color.White,
                shape = CircleShape
            ) { Icon(Icons.Default.Add, contentDescription = null) }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // 3. CATEGORÍAS FUNCIONALES
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categorias) { cat ->
                        FilterChip(
                            selected = categoriaSeleccionada == cat,
                            onClick = { categoriaSeleccionada = cat }, // Actualiza el estado
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
                Text("Resultados (${recetasFiltradas.size})", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }

            // 4. LISTA FILTRADA
            items(recetasFiltradas, key = { it.id }) { receta ->
                Card(
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Box {
                            Image(
                                painter = painterResource(id = receta.imagenRes),
                                contentDescription = receta.nombre,
                                modifier = Modifier.fillMaxWidth().height(200.dp),
                                contentScale = ContentScale.Crop
                            )

                            // BOTONES DE EDICIÓN/BORRADO (Se mantienen igual)
                            Row(
                                modifier = Modifier.align(Alignment.BottomEnd).padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    modifier = Modifier.size(36.dp),
                                    color = Color(0xFF4285F4),
                                    shape = RoundedCornerShape(8.dp),
                                    onClick = {
                                        recetaAEditar = receta
                                        nombreInput = receta.nombre
                                        tiempoInput = receta.tiempo
                                        showDialog = true
                                    }
                                ) { Icon(Icons.Default.Edit, null, tint = Color.White, modifier = Modifier.padding(8.dp)) }

                                Surface(
                                    modifier = Modifier.size(36.dp),
                                    color = Color(0xFFEA4335),
                                    shape = RoundedCornerShape(8.dp),
                                    onClick = { listaRecetas.remove(receta) }
                                ) { Icon(Icons.Default.Delete, null, tint = Color.White, modifier = Modifier.padding(8.dp)) }
                            }
                        }

                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(receta.nombre, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), modifier = Modifier.weight(1f))
                                Icon(Icons.Default.Star, null, tint = Color(0xFFFFB300), modifier = Modifier.size(16.dp))
                                Text(" " + receta.calificacion, style = MaterialTheme.typography.bodySmall)
                            }
                            // ... resto de la info
                        }
                    }
                }
            }

            // Botón "Agregar nueva receta" punteado al final...
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