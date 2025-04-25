import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ejercicio3.Screen.Animal
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun ScreenB(navController: NavController) {
    val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle

    var animales by remember { mutableStateOf(listOf<Animal>()) }
    var editIndex by remember { mutableStateOf(-1) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var animalToDeleteIndex by remember { mutableStateOf(-1) }

    LaunchedEffect(Unit) {
        val newAnimal = savedStateHandle?.get<Animal>("animal")
        if (newAnimal != null && !animales.contains(newAnimal)) {
            animales = animales + newAnimal
            savedStateHandle.remove<Animal>("animal")
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Carnets de mascotas",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(animales.size) { index ->
                val animal = animales[index]

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        AsyncImage(
                            model = animal.foto,
                            contentDescription = "Foto de ${animal.nombre}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoRow("Nombre:", animal.nombre)
                        InfoRow("Raza:", animal.raza)
                        InfoRow("Tamaño:", animal.tamano)
                        InfoRow("Edad:", animal.edad)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(onClick = {
                                animalToDeleteIndex = index
                                showDeleteDialog = true
                            }) {
                                Text("Eliminar")
                            }

                            Button(onClick = {
                                editIndex = index
                            }) {
                                Text("Editar")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }

    if (editIndex != -1) {
        val animal = animales[editIndex]
        EditarAnimalDialog(
            animal = animal,
            onDismiss = { editIndex = -1 },
            onSave = { editado ->
                animales = animales.toMutableList().also { it[editIndex] = editado }
                editIndex = -1
            }
        )
    }

    if (showDeleteDialog && animalToDeleteIndex != -1) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                Button(onClick = {
                    animales = animales.toMutableList().also { it.removeAt(animalToDeleteIndex) }
                    showDeleteDialog = false
                    animalToDeleteIndex = -1
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDeleteDialog = false
                    animalToDeleteIndex = -1
                }) {
                    Text("No")
                }
            },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro que deseas eliminar este carnet?") }
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(80.dp)
        )
        Text(text = value)
    }
}

@Composable
fun EditarAnimalDialog(
    animal: Animal,
    onDismiss: () -> Unit,
    onSave: (Animal) -> Unit
) {
    var nombre by remember { mutableStateOf(animal.nombre) }
    var raza by remember { mutableStateOf(animal.raza) }
    var tamano by remember { mutableStateOf(animal.tamano) }
    var edad by remember { mutableStateOf(animal.edad) } // Ahora acepta texto
    var foto by remember { mutableStateOf(animal.foto) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                val nuevoAnimal = Animal(
                    nombre = nombre,
                    raza = raza,
                    tamano = tamano,
                    edad = edad,
                    foto = foto
                )
                onSave(nuevoAnimal)
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text("Editar mascota") },
        text = {
            Column {
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
                OutlinedTextField(value = raza, onValueChange = { raza = it }, label = { Text("Raza") })
                OutlinedTextField(value = tamano, onValueChange = { tamano = it }, label = { Text("Tamaño") })
                OutlinedTextField(value = edad, onValueChange = { edad = it }, label = { Text("Edad") })
                OutlinedTextField(value = foto, onValueChange = { foto = it }, label = { Text("URL de la foto") })
            }
        }
    )
}
