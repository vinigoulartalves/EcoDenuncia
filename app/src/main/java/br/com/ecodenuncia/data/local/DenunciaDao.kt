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

    @Query("SELECT * FROM denuncias ORDER BY dataCriacao DESC")
    fun getAll(): Flow<List<Denuncia>>

    @Query("SELECT * FROM denuncias WHERE id = :id")
    fun getById(id: Long): Flow<Denuncia?>

    @Query("SELECT * FROM denuncias WHERE id = :id")
    suspend fun getByIdOnce(id: Long): Denuncia?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(denuncia: Denuncia): Long

    @Update
    suspend fun update(denuncia: Denuncia)

    @Delete
    suspend fun delete(denuncia: Denuncia)
}
