package br.com.ecodenuncia.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.ecodenuncia.viewmodel.CampoFormularioDenuncia
import br.com.ecodenuncia.viewmodel.DenunciaViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NovaDenunciaScreen(
    viewModel: DenunciaViewModel,
    onVoltar: () -> Unit,
    onSalvarRascunho: (Long) -> Unit
) {
    val context = LocalContext.current
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    var exibirErros by rememberSaveable { mutableStateOf(false) }
    var mensagemCaptura by rememberSaveable { mutableStateOf<String?>(null) }
    var pendingPhotoUri by rememberSaveable { mutableStateOf<String?>(null) }

    val tituloInvalido = exibirErros && formState.titulo.isBlank()
    val descricaoInvalida = exibirErros && formState.descricao.isBlank()
    val tipoResiduoInvalido = exibirErros && formState.tipoResiduo.isBlank()

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        val uri = pendingPhotoUri
        if (success && !uri.isNullOrBlank()) {
            viewModel.atualizarCampoFormulario(CampoFormularioDenuncia.FOTO_URI, uri)
            mensagemCaptura = null
        } else {
            mensagemCaptura = "Não foi possível capturar a imagem. Tente novamente."
        }
        pendingPhotoUri = null
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Nova denúncia",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Preencha os dados iniciais para continuar.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = formState.titulo,
                onValueChange = {
                    viewModel.atualizarCampoFormulario(CampoFormularioDenuncia.TITULO, it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Título") },
                isError = tituloInvalido,
                supportingText = {
                    if (tituloInvalido) {
                        Text("Informe o título da denúncia.")
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = formState.descricao,
                onValueChange = {
                    viewModel.atualizarCampoFormulario(CampoFormularioDenuncia.DESCRICAO, it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Descrição") },
                isError = descricaoInvalida,
                supportingText = {
                    if (descricaoInvalida) {
                        Text("Informe a descrição da denúncia.")
                    }
                },
                minLines = 3,
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = formState.tipoResiduo,
                onValueChange = {
                    viewModel.atualizarCampoFormulario(CampoFormularioDenuncia.TIPO_RESIDUO, it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Tipo de resíduo") },
                isError = tipoResiduoInvalido,
                supportingText = {
                    if (tipoResiduoInvalido) {
                        Text("Informe o tipo de resíduo.")
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Button(
                onClick = {
                    val uri = criarUriDaFoto(context)
                    if (uri != null) {
                        pendingPhotoUri = uri.toString()
                        cameraLauncher.launch(uri)
                    } else {
                        mensagemCaptura = "Falha ao preparar arquivo da foto."
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Tirar foto")
            }

            formState.fotoUri?.let { fotoUri ->
                Text(
                    text = "Pré-visualização da foto:",
                    style = MaterialTheme.typography.labelLarge
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f))
                ) {
                    ImagePreview(
                        imageUri = fotoUri,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 160.dp, max = 240.dp)
                    )
                }
            }

            mensagemCaptura?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    exibirErros = true
                    if (formState.titulo.isNotBlank() &&
                        formState.descricao.isNotBlank() &&
                        formState.tipoResiduo.isNotBlank()
                    ) {
                        viewModel.salvarDenuncia(onSalvarRascunho)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Continuar")
            }

            TextButton(onClick = onVoltar, modifier = Modifier.fillMaxWidth()) {
                Text("Voltar")
            }
        }
    }
}

@Composable
private fun ImagePreview(
    imageUri: String,
    modifier: Modifier = Modifier
) {
    val uri = remember(imageUri) { Uri.parse(imageUri) }

    AndroidView(
        factory = { context ->
            android.widget.ImageView(context).apply {
                scaleType = android.widget.ImageView.ScaleType.CENTER_CROP
                adjustViewBounds = true
                setBackgroundColor(android.graphics.Color.LTGRAY)
            }
        },
        update = { imageView ->
            imageView.setImageURI(uri)
        },
        modifier = modifier
    )
}

private fun criarUriDaFoto(context: android.content.Context): Uri? {
    return runCatching {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imagesDir = File(context.filesDir, "denuncias_fotos").apply {
            if (!exists()) mkdirs()
        }
        val imageFile = File(imagesDir, "DENUNCIA_${timeStamp}.jpg")

        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            imageFile
        )
    }.getOrNull()
}
