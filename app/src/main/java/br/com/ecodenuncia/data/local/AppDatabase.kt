package br.com.ecodenuncia.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.ecodenuncia.model.Denuncia

@Database(entities = [Denuncia::class], version = 1, exportSchema = false)
@TypeConverters(StatusConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun denunciaDao(): DenunciaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "eco_denuncia.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
