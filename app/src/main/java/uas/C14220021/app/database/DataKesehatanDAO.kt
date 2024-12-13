package nit2x.paba.projectroom.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uas.C14220021.app.database.DataKesehatan

@Dao
interface DataKesehatanDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: DataKesehatan)

    @Query("UPDATE DataKesehatan SET tanggal=:isi_tanggal, jam=:isi_jam, beratbadan=:isi_berat, tekanandarah=:isi_tekanan,catatan=:isi_catatan WHERE id=:pilihid")
    fun update(isi_tanggal: String, isi_jam:String, isi_berat:String,isi_tekanan:String,isi_catatan: String , pilihid: Int)


    @Delete
    fun delete(daftar: DataKesehatan)

    @Query("SELECT * FROM DataKesehatan ORDER BY id asc")
    fun selectAll() : MutableList<DataKesehatan>

    @Query("SELECT * FROM DataKesehatan WHERE id=:isi_id")
    suspend fun getTanggal(isi_id :Int) : DataKesehatan
}