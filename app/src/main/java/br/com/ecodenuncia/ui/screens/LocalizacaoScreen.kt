package br.com.ecodenuncia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LocalizacaoScreen(
    onVoltar: () -> Unit,
    onContinuar: () -> Unit,
    onSalvarEndereco: (String, String, String) -> Unit
) {
    var endereco by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Localização")
        OutlinedTextField(value = endereco, onValueChange = { endereco = it }, label = { Text("Endereço") })
        OutlinedTextField(value = bairro, onValueChange = { bairro = it }, label = { Text("Bairro") })
        OutlinedTextField(value = cidade, onValueChange = { cidade = it }, label = { Text("Cidade") })

        erro?.let { Text(it) }

        Button(onClick = {
            if (endereco.isBlank() || cidade.isBlank()) {
                erro = "Preencha endereço e cidade."
            } else {
                erro = null
                onSalvarEndereco(endereco, bairro, cidade)
                onContinuar()
            }
        }) {
            Text("Ir para revisão")
        }

        Button(onClick = onVoltar) { Text("Voltar") }
    }
}
