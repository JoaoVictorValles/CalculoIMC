package com.joao.calculoimc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable

fun LinearGradient() {
    val keyboardController = LocalSoftwareKeyboardController.current

    val gradient = Brush.linearGradient(
        0.0f to Color.Magenta,
        500.0f to Color.Cyan,
        start = Offset.Zero,
        end = Offset.Infinite
    )

    var peso by remember { mutableStateOf("") }
    var alt by remember { mutableStateOf("") }
    var resultado by remember{ mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "CÁLCULO DO IMC", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text(text = "Peso") },
            singleLine = true,
            placeholder = { Text("KG") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = alt,
            onValueChange = { alt = it },
            label = { Text(text = "Altura") },
            singleLine = true,
            placeholder = { Text("Ex: 1.80 ") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(onClick = {
            keyboardController?.hide()
            val pesoFloat = peso.toFloatOrNull()
            val altFloat = if (alt.contains(".")) {
                alt.toFloatOrNull()
            } else {
                alt.toFloatOrNull()?.div(100)
            }

            if (pesoFloat != null && altFloat != null && altFloat != 0f){
                var imc = pesoFloat / (altFloat * altFloat)

                val classificacao = when {
                    imc < 18.5 -> "Abaixo do peso"
                    imc < 25 -> "Peso normal"
                    imc < 30 -> "Sobrepeso"
                    imc < 35 -> "Obesidade grau 1"
                    imc < 40 -> "Obesidade grau 2"
                    else -> "Obesidade grau 3"
                }

                resultado = "Seu IMC é: %.2f\nClassificação: %s".format(imc, classificacao)
            }else{
                resultado = "Valores inválidos"
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(55.dp),
            colors = ButtonDefaults.buttonColors(Color.Black)



        ) { Text(text = "CALCULAR ") }


        Text(text = resultado ) // Passar a variavel resultado para cá!!!


    }

}


//O IMC, ou Índice de Massa Corporal,
// é calculado dividindo o peso (em kg) pela altura ao quadrado (em metros).
// A fórmula é: IMC = peso (kg) / altura² (m²). Por exemplo, se uma pessoa pesa 70 kg e tem 1,75 m de altura,
// seu IMC seria 70 / (1,75 * 1,75) = 22,86