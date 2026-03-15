package br.com.ecodenuncia.data.local

import androidx.room.TypeConverter
import br.com.ecodenuncia.model.StatusDenuncia

class StatusConverter {
    @TypeConverter
    fun toStatus(value: String): StatusDenuncia = StatusDenuncia.valueOf(value)

    @TypeConverter
    fun fromStatus(status: StatusDenuncia): String = status.name
}
