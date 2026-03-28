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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraDePropinasTheme {
                CalculadoraScreen()
            }
        }
    }
}

@Composable
fun CalculadoraScreen() {
    val BackgroundPastel = colorResource(id = R.color.background_pastel)
    val DarkBrown = colorResource(id = R.color.dark_brown)
    val PinkSuave = colorResource(id = R.color.pink_pastel_suave)
    val PinkFresa = colorResource(id = R.color.pink_pastel_fresa)
    val BlueMenta = colorResource(id = R.color.blue_pastel_menta)
    val VanillaPastel = colorResource(id = R.color.vanilla_pastel)
    val White = colorResource(id = R.color.white)

    var subtotalInput by remember { mutableStateOf("") }
    var tipPercentage by remember { mutableStateOf(0.0) }
    var showTicket by remember { mutableStateOf(false) }

    val subtotal = subtotalInput.toDoubleOrNull() ?: 0.0
    val tipAmount = subtotal * tipPercentage
    val totalAmount = subtotal + tipAmount

    if (showTicket) {
        PrintTicket(
            subtotal = subtotal,
            tipAmount = tipAmount,
            total = totalAmount,
            onBack = {
                showTicket = false
                subtotalInput = ""
                tipPercentage = 0.0
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundPastel)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.helados),
                contentDescription = "Logo Heladería",
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "こんにちは \n ソイ・レスリー",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBrown,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = subtotalInput,
                onValueChange = { subtotalInput = it },
                label = { Text("Subtotal del Pedido ($)", color = DarkBrown.copy(alpha = 0.7f)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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

            Text(
                text = "¡Añade un toque de propina!",
                fontSize = 16.sp,
                color = DarkBrown,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TipButton("0%", VanillaPastel, DarkBrown, Modifier.weight(1f)) { tipPercentage = 0.0 }
                TipButton("10%", PinkSuave, DarkBrown, Modifier.weight(1f)) { tipPercentage = 0.10 }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TipButton("15%", PinkFresa, DarkBrown, Modifier.weight(1f)) { tipPercentage = 0.15 }
                TipButton("20%", BlueMenta, DarkBrown, Modifier.weight(1f)) { tipPercentage = 0.20 }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Propina:", fontSize = 16.sp, color = DarkBrown)
                        Text(
                            text = "$${String.format("%.2f", tipAmount)}",
                            fontSize = 16.sp,
                            color = DarkBrown,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = BackgroundPastel.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total a Pagar:", fontSize = 20.sp, color = DarkBrown, fontWeight = FontWeight.Bold)
                        Text(
                            text = "$${String.format("%.2f", totalAmount)}",
                            fontSize = 24.sp,
                            color = DarkBrown,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { if (subtotal > 0) showTicket = true },
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

@Composable
fun TipButton(texto: String, backgroundColor: Color, contentColor: Color, modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
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