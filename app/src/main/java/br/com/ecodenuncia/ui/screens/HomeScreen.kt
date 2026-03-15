package br.com.ecodenuncia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNovaDenuncia: () -> Unit,
    onListarDenuncias: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Painel principal", style = MaterialTheme.typography.headlineSmall)
        Button(onClick = onNovaDenuncia, modifier = Modifier.padding(top = 16.dp)) {
            Text("Nova denúncia")
        }
        Button(onClick = onListarDenuncias, modifier = Modifier.padding(top = 8.dp)) {
            Text("Minhas denúncias")
        }
    }
}
