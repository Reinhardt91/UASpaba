package nit2x.paba.projectroom.database

import android.content.Context
import android.provider.ContactsContract.Data
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uas.C14220021.app.database.DataKesehatan

@Database(entities = [DataKesehatan::class], version = 1)
abstract class DataKesehatanDB : RoomDatabase() {
    abstract fun funDataKesehatanDAO() : DataKesehatan

    companion object {
        @Volatile
        private var INSTANCE: DataKesehatanDB? = null

        @JvmStatic
        fun getDatabase(context: Context): DataKesehatanDB {
            if (INSTANCE == null) {
                synchronized(DataKesehatanDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DataKesehatanDB::class.java,
                        "DataKesehatan_db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as DataKesehatanDB
        }
    }
}
