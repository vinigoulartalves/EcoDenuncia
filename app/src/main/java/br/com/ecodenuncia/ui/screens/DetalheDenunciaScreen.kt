package br.com.ecodenuncia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.ecodenuncia.model.Denuncia
import br.com.ecodenuncia.model.StatusDenuncia

@Composable
fun DetalheDenunciaScreen(
    denuncia: Denuncia,
    onVoltar: () -> Unit,
    onEnviar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Detalhes da denúncia")
        Text("Título: ${denuncia.titulo}")
        Text("Descrição: ${denuncia.descricao}")
        Text("Tipo: ${denuncia.tipoResiduo}")
        Text("Endereço: ${denuncia.endereco}")
        Text("Bairro: ${denuncia.bairro}")
        Text("Cidade: ${denuncia.cidade}")
        Text("Status: ${denuncia.status}")

        if (denuncia.status != StatusDenuncia.ENVIADA) {
            Button(onClick = onEnviar) {
                Text("Simular envio")
            }
        }

        Button(onClick = onVoltar) {
            Text("Voltar")
        }
    }
}
