package com.example.calculadoradepropinas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Componente que simula la vista de un recibo impreso
// Recibe los cálculos matemáticos como parámetros y la función de regreso (onBack)
@Composable
fun PrintTicket(subtotal: Double, tipAmount: Double, total: Double, onBack: () -> Unit) {
    // Colores específicos para esta pantalla
    val BackgroundPastel = colorResource(id = R.color.background_pastel)
    val DarkBrown = colorResource(id = R.color.dark_brown)
    val White = colorResource(id = R.color.white)
    val PinkFresa = colorResource(id = R.color.pink_pastel_fresa)

    // Contenedor principal que centra el ticket en la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPastel)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Tarjeta que representa el papel del recibo (con esquinas rectas para simular papel)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = androidx.compose.foundation.shape.AbsoluteCutCornerShape(0.dp) // Esquinas cuadradas
        ) {
            // Contenido interno del ticket
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Encabezado del ticket
                Text(
                    text = "こんにちは",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DarkBrown,
                    letterSpacing = 6.sp
                )
                Text(
                    text = "ソイ・レスリー",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBrown,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "ICE CREAM SHOP",
                    fontSize = 14.sp,
                    color = DarkBrown.copy(alpha = 0.7f),
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Logo en miniatura dentro del ticket
                Image(
                    painter = painterResource(id = R.drawable.helados),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))
                // Separador simulando una línea punteada de impresora
                Text("------------------------------------------", color = Color.LightGray)

                // Detalles del ticket utilizando el componente auxiliar "TicketLine"
                TicketLine("SUBTOTAL", "$${String.format("%.2f", subtotal)}", DarkBrown)
                TicketLine("PROPINA", "$${String.format("%.2f", tipAmount)}", DarkBrown)

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(thickness = 1.dp, color = DarkBrown.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(12.dp))

                // Línea del total destacada (con parámetro isBold = true)
                TicketLine("TOTAL A PAGAR", "$${String.format("%.2f", total)}", PinkFresa, isBold = true)

                Spacer(modifier = Modifier.height(24.dp))

                // Mensaje de despedida al final del recibo
                Text(
                    text = "¡GRACIAS POR TU VISITA!",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = DarkBrown.copy(alpha = 0.5f)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón para cerrar el ticket y volver a la pantalla principal
        Button(
            onClick = onBack, // Dispara la acción de reseteo definida en MainActivity
            modifier = Modifier.fillMaxWidth().height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBrown),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
        ) {
            Text("NUEVA CUENTA", color = White, fontWeight = FontWeight.Bold)
        }
    }
}

// Componente auxiliar reutilizable para estructurar cada línea de costo en el recibo (Etiqueta + Valor)
@Composable
fun TicketLine(label: String, value: String, colorValue: Color, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween // Separa la etiqueta a la izquierda y el valor a la derecha
    ) {
        // Configuraciones dinámicas de fuente dependiendo de si es la línea de "Total" o no
        val style = if (isBold) FontWeight.Black else FontWeight.Normal
        val size = if (isBold) 20.sp else 16.sp

        Text(text = label, fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
        Text(text = value, fontSize = size, color = colorValue, fontWeight = style)
    }
}