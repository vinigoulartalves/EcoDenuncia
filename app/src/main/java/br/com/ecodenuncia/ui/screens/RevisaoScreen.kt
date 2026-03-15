package br.com.ecodenuncia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.ecodenuncia.model.Denuncia

@Composable
fun RevisaoScreen(
    denuncia: Denuncia,
    onVoltar: () -> Unit,
    onEnviar: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SnackbarHost(hostState = snackbarHostState)
        Text("Revisão")
        Text("Título: ${denuncia.titulo}")
        Text("Descrição: ${denuncia.descricao}")
        Text("Tipo de resíduo: ${denuncia.tipoResiduo}")
        Text("Endereço: ${denuncia.endereco}")
        Text("Cidade: ${denuncia.cidade}")

        Button(onClick = onEnviar) {
            Text("Enviar denúncia")
        }

        Button(onClick = onVoltar) {
            Text("Voltar")
        }
    }
}
