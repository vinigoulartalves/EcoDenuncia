package br.com.ecodenuncia.data.repository

import br.com.ecodenuncia.data.local.DenunciaDao
import br.com.ecodenuncia.model.Denuncia
import br.com.ecodenuncia.model.StatusDenuncia
import kotlinx.coroutines.flow.Flow

class DenunciaRepository(
    private val dao: DenunciaDao
) {
    fun listarDenuncias(): Flow<List<Denuncia>> = dao.getAll()

    fun buscarPorId(id: Long): Flow<Denuncia?> = dao.getById(id)

    suspend fun buscarPorIdUmaVez(id: Long): Denuncia? = dao.getByIdOnce(id)

    suspend fun salvarRascunho(denuncia: Denuncia): Long {
        return dao.insert(denuncia.copy(status = StatusDenuncia.RASCUNHO))
    }

    suspend fun atualizar(denuncia: Denuncia) = dao.update(denuncia)

    suspend fun enviarDenuncia(denuncia: Denuncia) {
        dao.update(denuncia.copy(status = StatusDenuncia.ENVIADA))
    }

    suspend fun excluir(denuncia: Denuncia) = dao.delete(denuncia)
}
