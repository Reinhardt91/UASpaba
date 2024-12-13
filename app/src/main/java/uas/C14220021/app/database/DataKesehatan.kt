package uas.C14220021.app.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class DataKesehatan(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var id: Int = 0,

    @ColumnInfo(name="tanggal")
    var tanggal: String? = null,

    @ColumnInfo(name="jam")
    var jam: String? = null,

    @ColumnInfo(name="beratbadan")
    var beratbadan : String? = null,

    @ColumnInfo(name= "tekanandarah")
    var tekanandarah : String? = null,

    @ColumnInfo(name="catatan")
    var catatan: String? = null,
)
