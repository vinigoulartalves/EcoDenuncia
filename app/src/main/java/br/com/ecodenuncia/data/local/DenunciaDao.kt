package br.com.ecodenuncia.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.ecodenuncia.model.Denuncia
import kotlinx.coroutines.flow.Flow

@Dao
interface DenunciaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(denuncia: Denuncia): Long

    @Query("SELECT * FROM denuncias ORDER BY dataCriacao DESC")
    fun listar(): Flow<List<Denuncia>>

    @Query("SELECT * FROM denuncias WHERE id = :id")
    fun buscarPorId(id: Long): Flow<Denuncia?>

    @Query("SELECT * FROM denuncias WHERE id = :id")
    suspend fun buscarPorIdUmaVez(id: Long): Denuncia?

    @Update
    suspend fun atualizar(denuncia: Denuncia)

    @Delete
    suspend fun excluir(denuncia: Denuncia)

    // Compatibilidade com nomes antigos já usados em outras camadas.
    fun getAll(): Flow<List<Denuncia>> = listar()
    fun getById(id: Long): Flow<Denuncia?> = buscarPorId(id)
    suspend fun getByIdOnce(id: Long): Denuncia? = buscarPorIdUmaVez(id)
    suspend fun insert(denuncia: Denuncia): Long = inserir(denuncia)
    suspend fun update(denuncia: Denuncia) = atualizar(denuncia)
    suspend fun delete(denuncia: Denuncia) = excluir(denuncia)
}
