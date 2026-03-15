package br.com.ecodenuncia.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.ecodenuncia.model.Denuncia
import br.com.ecodenuncia.ui.components.StatusChip

@Composable
fun ListaDenunciasScreen(
    denuncias: List<Denuncia>,
    onVoltar: () -> Unit,
    onDetalhes: (Long) -> Unit,
    onExcluir: (Denuncia) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Minhas denúncias")

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(denuncias, key = { it.id }) { denuncia ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDetalhes(denuncia.id) }
                ) {
                    Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(denuncia.titulo)
                            Text(denuncia.cidade)
                        }
                        StatusChip(denuncia.status)
                    }
                    Button(onClick = { onExcluir(denuncia) }, modifier = Modifier.padding(12.dp)) {
                        Text("Excluir")
                    }
                }
            }
        }

        Button(onClick = onVoltar) {
            Text("Voltar")
        }
    }
}
