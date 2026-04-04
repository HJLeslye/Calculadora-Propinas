package com.example.calculadoradepropinas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoradepropinas.ui.theme.CalculadoraDePropinasTheme

// Clase principal que inicializa la aplicación en Android
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplicamos el tema visual general de la app
            CalculadoraDePropinasTheme {
                // Llamamos al componente principal (la pantalla de la calculadora)
                CalculadoraScreen()
            }
        }
    }
}

// Componente principal de la interfaz de la calculadora
@Composable
fun CalculadoraScreen() {
    // --- 1. Definición de Paleta de Colores ---
    // Cargamos los colores desde los recursos (res/values/colors.xml)
    val BackgroundPastel = colorResource(id = R.color.background_pastel)
    val DarkBrown = colorResource(id = R.color.dark_brown)
    val PinkSuave = colorResource(id = R.color.pink_pastel_suave)
    val PinkFresa = colorResource(id = R.color.pink_pastel_fresa)
    val BlueMenta = colorResource(id = R.color.blue_pastel_menta)
    val VanillaPastel = colorResource(id = R.color.vanilla_pastel)
    val White = colorResource(id = R.color.white)

    // --- 2. Variables de Estado (State) ---
    // Estas variables reaccionan a los cambios. Si su valor cambia, la UI se actualiza automáticamente.
    var subtotalInput by remember { mutableStateOf("") } // Guarda el texto que el usuario escribe
    var tipPercentage by remember { mutableStateOf(0.0) } // Guarda el porcentaje de propina seleccionado
    var showTicket by remember { mutableStateOf(false) } // Controla si se muestra el ticket o la calculadora

    // --- 3. Cálculos Lógicos ---
    // Convertimos el texto ingresado a un número decimal (Double). Si está vacío o es inválido, usamos 0.0
    val subtotal = subtotalInput.toDoubleOrNull() ?: 0.0
    // Calculamos el monto de la propina y el total final
    val tipAmount = subtotal * tipPercentage
    val totalAmount = subtotal + tipAmount

    // --- 4. Renderizado de Interfaz (Navegación condicional) ---
    // Si el usuario presionó "Imprimir Recibo", mostramos la pantalla del Ticket
    if (showTicket) {
        PrintTicket(
            subtotal = subtotal,
            tipAmount = tipAmount,
            total = totalAmount,
            onBack = {
                // Función callback: Reinicia los valores cuando el usuario regresa a crear una nueva cuenta
                showTicket = false
                subtotalInput = ""
                tipPercentage = 0.0
            }
        )
    } else {
        // Si no se está mostrando el ticket, mostramos la pantalla de captura de datos
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundPastel)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Centramos todo horizontalmente
        ) {
            // Logo de la heladería
            Image(
                painter = painterResource(id = R.drawable.helados),
                contentDescription = "Logo Heladería",
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 16.dp)
            )

            // Título o saludo de la pantalla
            Text(
                text = "こんにちは \n ソイ・レスリー",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBrown,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campo de texto para ingresar el subtotal numérico
            OutlinedTextField(
                value = subtotalInput,
                onValueChange = { subtotalInput = it }, // Actualiza el estado cuando el usuario escribe
                label = { Text("Subtotal del Pedido ($)", color = DarkBrown.copy(alpha = 0.7f)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Muestra teclado numérico
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BlueMenta,
                    unfocusedBorderColor = PinkFresa,
                    focusedContainerColor = VanillaPastel,
                    unfocusedContainerColor = VanillaPastel,
                    focusedTextColor = DarkBrown,
                    unfocusedTextColor = DarkBrown
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Texto indicativo para la sección de propinas
            Text(
                text = "¡Añade un toque de propina!",
                fontSize = 16.sp,
                color = DarkBrown,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Primera fila de botones de propina (0% y 10%)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Usamos el componente reutilizable "TipButton" y actualizamos el estado tipPercentage al hacer clic
                TipButton("0%", VanillaPastel, DarkBrown, Modifier.weight(1f)) { tipPercentage = 0.0 }
                TipButton("10%", PinkSuave, DarkBrown, Modifier.weight(1f)) { tipPercentage = 0.10 }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Segunda fila de botones de propina (15% y 20%)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TipButton("15%", PinkFresa, DarkBrown, Modifier.weight(1f)) { tipPercentage = 0.15 }
                TipButton("20%", BlueMenta, DarkBrown, Modifier.weight(1f)) { tipPercentage = 0.20 }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tarjeta que muestra el resumen parcial (Propina y Total)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Fila que muestra la cantidad de propina calculada
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Propina:", fontSize = 16.sp, color = DarkBrown)
                        Text(
                            text = "$${String.format("%.2f", tipAmount)}", // Formato a 2 decimales
                            fontSize = 16.sp,
                            color = DarkBrown,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = BackgroundPastel.copy(alpha = 0.5f)) // Línea separadora
                    Spacer(modifier = Modifier.height(10.dp))

                    // Fila que muestra el total final a pagar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total a Pagar:", fontSize = 20.sp, color = DarkBrown, fontWeight = FontWeight.Bold)
                        Text(
                            text = "$${String.format("%.2f", totalAmount)}", // Formato a 2 decimales
                            fontSize = 24.sp,
                            color = DarkBrown,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Empuja el botón final hacia la parte inferior de la pantalla
            Spacer(modifier = Modifier.weight(1f))

            // Botón para generar el ticket
            Button(
                onClick = { if (subtotal > 0) showTicket = true }, // Solo permite avanzar si hay un subtotal mayor a 0
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkBrown),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = "IMPRIMIR RECIBO",
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// Componente reutilizable para los botones de selección de porcentaje de propina
@Composable
fun TipButton(texto: String, backgroundColor: Color, contentColor: Color, modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick, // Ejecuta la acción pasada por parámetro
        modifier = modifier.height(55.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(14.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Text(texto, fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    }
}