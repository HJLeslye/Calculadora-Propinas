package com.example.calculadoradepropinas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TicketActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val total = intent.getStringExtra("TOTAL") ?: "0.00"
        val propina = intent.getStringExtra("PROPINA") ?: "0.00"

        setContent {
            Column(
                modifier = Modifier.fillMaxSize().background(Color.White).padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("FUWAFUWA ICE CREAM", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Calle Japón #123", fontSize = 12.sp)
                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider()
                Row(Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Propina:")
                    Text("$${propina}")
                }
                Row(Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("TOTAL:", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Text("$${total}", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                }
                HorizontalDivider()
                Spacer(modifier = Modifier.height(40.dp))
                Text("¡Arigato gozaimasu!", fontWeight = FontWeight.Medium)

                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { finish() }) { Text("VOLVER") }
            }
        }
    }
}