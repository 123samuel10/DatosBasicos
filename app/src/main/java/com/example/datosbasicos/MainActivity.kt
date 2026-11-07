
package com.example.datosbasicos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                DatosBasicosScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatosBasicosScreen() {

    // Variables para guardar los datos ingresados
    var nombre by remember { mutableStateOf("") }
    var anioNacimiento by remember { mutableStateOf("") }
    var anioActual by remember { mutableStateOf("") }

    // Variables para mostrar los resultados
    var edad by remember { mutableIntStateOf(0) }
    var mostrarResultado by remember { mutableStateOf(false) }

    var nombreResultado by remember { mutableStateOf("") }
    var nacimientoResultado by remember { mutableStateOf("") }
    var actualResultado by remember { mutableStateOf("") }

    // Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(

        // Barra superior
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "DatosBásicos")
                }
            )
        },

        // Snackbar
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },

        // Botón flotante
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                    val nacimiento = anioNacimiento.toIntOrNull()
                    val actual = anioActual.toIntOrNull()

                    if (
                        nombre.isNotBlank() &&
                        nacimiento != null &&
                        actual != null &&
                        nacimiento > 0 &&
                        actual >= nacimiento
                    ) {

                        // Calcular la edad
                        edad = actual - nacimiento

                        // Guardar los datos para mostrarlos
                        nombreResultado = nombre
                        nacimientoResultado = anioNacimiento
                        actualResultado = anioActual

                        mostrarResultado = true

                        // Mostrar Snackbar
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Datos calculados correctamente"
                            )
                        }

                    } else {

                        mostrarResultado = false

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Ingrese datos válidos"
                            )
                        }
                    }
                }
            ) {
                Text(text = "Calcular")
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Campo Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                },
                label = {
                    Text("Nombre")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo Año de nacimiento
            OutlinedTextField(
                value = anioNacimiento,
                onValueChange = {
                    anioNacimiento = it
                },
                label = {
                    Text("Año de nacimiento")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo Año actual
            OutlinedTextField(
                value = anioActual,
                onValueChange = {
                    anioActual = it
                },
                label = {
                    Text("Año actual")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Mostrar resultados
            if (mostrarResultado) {

                HorizontalDivider()

                Text(
                    text = "Resultados",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Nombre: $nombreResultado"
                )

                Text(
                    text = "Año de nacimiento: $nacimientoResultado"
                )

                Text(
                    text = "Año actual: $actualResultado"
                )

                Text(
                    text = "Edad: $edad años"
                )
            }
        }
    }
}
