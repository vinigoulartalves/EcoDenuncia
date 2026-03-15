package br.com.ecodenuncia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.ecodenuncia.data.repository.DenunciaRepository
import br.com.ecodenuncia.model.Denuncia
import br.com.ecodenuncia.model.StatusDenuncia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class CampoFormularioDenuncia {
    TITULO,
    DESCRICAO,
    TIPO_RESIDUO,
    ENDERECO,
    BAIRRO,
    CIDADE,
    OBSERVACOES,
    FOTO_URI
}

data class DenunciaFormState(
    val titulo: String = "",
    val descricao: String = "",
    val tipoResiduo: String = "",
    val endereco: String = "",
    val bairro: String = "",
    val cidade: String = "",
    val observacoes: String = "",
    val fotoUri: String? = null
) {
    fun toDenuncia(status: StatusDenuncia = StatusDenuncia.RASCUNHO): Denuncia {
        return Denuncia(
            titulo = titulo,
            descricao = descricao,
            tipoResiduo = tipoResiduo,
            endereco = endereco,
            bairro = bairro,
            cidade = cidade,
            observacoes = observacoes,
            fotoUri = fotoUri,
            status = status
        )
    }
}

class DenunciaViewModel(
    private val repository: DenunciaRepository
) : ViewModel() {

    private val _formState = MutableStateFlow(DenunciaFormState())
    val formState: StateFlow<DenunciaFormState> = _formState.asStateFlow()

    val denuncias: StateFlow<List<Denuncia>> = repository
        .listar()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun atualizarCampoFormulario(campo: CampoFormularioDenuncia, valor: String) {
        _formState.update { atual ->
            when (campo) {
                CampoFormularioDenuncia.TITULO -> atual.copy(titulo = valor)
                CampoFormularioDenuncia.DESCRICAO -> atual.copy(descricao = valor)
                CampoFormularioDenuncia.TIPO_RESIDUO -> atual.copy(tipoResiduo = valor)
                CampoFormularioDenuncia.ENDERECO -> atual.copy(endereco = valor)
                CampoFormularioDenuncia.BAIRRO -> atual.copy(bairro = valor)
                CampoFormularioDenuncia.CIDADE -> atual.copy(cidade = valor)
                CampoFormularioDenuncia.OBSERVACOES -> atual.copy(observacoes = valor)
                CampoFormularioDenuncia.FOTO_URI -> atual.copy(fotoUri = valor.ifBlank { null })
            }
        }
    }

    fun limparFormulario() {
        _formState.value = DenunciaFormState()
    }

    fun salvarDenuncia(onSalvo: (Long) -> Unit = {}) {
        viewModelScope.launch {
            val id = repository.inserir(_formState.value.toDenuncia())
            limparFormulario()
            onSalvo(id)
        }
    }

    fun buscarDenunciaPorId(id: Long): StateFlow<Denuncia?> {
        return repository
            .buscarPorId(id)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
    }

    fun atualizarStatusParaEnviada(id: Long) {
        viewModelScope.launch {
            val denuncia = repository.buscarPorIdUmaVez(id) ?: return@launch
            repository.atualizar(denuncia.copy(status = StatusDenuncia.ENVIADA))
        }
    }

    fun salvarRevisao(id: Long, onSalvo: () -> Unit = {}) {
        viewModelScope.launch {
            val denuncia = repository.buscarPorIdUmaVez(id) ?: return@launch
            repository.atualizar(denuncia.copy(status = StatusDenuncia.PENDENTE))
            onSalvo()
        }
    }

    fun enviarDaRevisao(id: Long, onEnviado: () -> Unit = {}) {
        viewModelScope.launch {
            val denuncia = repository.buscarPorIdUmaVez(id) ?: return@launch
            repository.atualizar(denuncia.copy(status = StatusDenuncia.ENVIADA))
            onEnviado()
        }
    }

    fun excluirDenuncia(id: Long) {
        viewModelScope.launch {
            val denuncia = repository.buscarPorIdUmaVez(id) ?: return@launch
            repository.excluir(denuncia)
        }
    }

    // Métodos de compatibilidade para telas já existentes no projeto.
    fun obterDenuncia(id: Long): StateFlow<Denuncia?> = buscarDenunciaPorId(id)

    fun criarRascunho(
        titulo: String,
        descricao: String,
        tipoResiduo: String,
        observacoes: String,
        onSalvo: (Long) -> Unit
    ) {
        _formState.value = _formState.value.copy(
            titulo = titulo,
            descricao = descricao,
            tipoResiduo = tipoResiduo,
            observacoes = observacoes
        )
        salvarDenuncia(onSalvo)
    }

    fun atualizarEndereco(
        id: Long,
        endereco: String,
        bairro: String,
        cidade: String,
        observacoes: String
    ) {
        viewModelScope.launch {
            val denuncia = repository.buscarPorIdUmaVez(id) ?: return@launch
            repository.atualizar(
                denuncia.copy(
                    endereco = endereco,
                    bairro = bairro,
                    cidade = cidade,
                    observacoes = observacoes,
                    status = StatusDenuncia.PENDENTE
                )
            )
        }
    }

    fun enviarDenuncia(denuncia: Denuncia) {
        viewModelScope.launch {
            repository.enviarDenuncia(denuncia)
        }
    }

    fun excluirDenuncia(denuncia: Denuncia) {
        viewModelScope.launch {
            repository.excluir(denuncia)
        }
    }
}

class DenunciaViewModelFactory(
    private val repository: DenunciaRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DenunciaViewModel(repository) as T
    }
}
