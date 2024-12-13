package nit2x.paba.projectroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import nit2x.paba.projectroom.database.DataKesehatan
import nit2x.paba.projectroom.database.DataKesehatanDB

class MainActivity : AppCompatActivity() {
    private lateinit var adapterDaftar: AdapterDaftar
    private var arDaftar : MutableList<DataKesehatan> = mutableListOf()

    private lateinit var DB : daftarBelanjaDB
    private lateinit var _fabAdd : FloatingActionButton
    private lateinit var _rvDaftar: RecyclerView

    private lateinit var historyAdapter: AdapterDaftar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        DB = daftarBelanjaDB.getDatabase(this)
        _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        _rvDaftar = findViewById(R.id.rvDaftar)




        _fabAdd.setOnClickListener{
            startActivity(Intent(this, TambahDaftar::class.java))
        }

        adapterDaftar = AdapterDaftar(arDaftar)

        var _rvDaftar = findViewById<RecyclerView>(R.id.rvDaftar)

        _rvDaftar.layoutManager = LinearLayoutManager(this)
        _rvDaftar.adapter = adapterDaftar

        adapterDaftar.setOnItemClickCallback(
            object : AdapterDaftar.OnItemClickCallback {

            }
        )


    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val daftarBelanja = DB.fundafarBelanjaDAO().selectAll()
            Log.d("data ROOM", daftarBelanja.toString())
            adapterDaftar.isiData(daftarBelanja)
        }
    }

    override fun delData(dtBelanja: daftarBelanja) {
        CoroutineScope(Dispatchers.IO).async {
            DB.fundafarBelanjaDAO().delete(dtBelanja)
            val TambahDaftar = DB.fundafarBelanjaDAO().selectAll()
            withContext(Dispatchers.Main) {
                adapterDaftar.isiData(TambahDaftar)
            }
        }
    }


    override fun pindahkanKeHistory(dtBelanja: daftarBelanja) {
        CoroutineScope(Dispatchers.IO).async {
            // Insert the data into historyBarang_DB
            DB.historyBarangDAO().insertHistory(dtBelanja)

            // Delete the data from daftarBelanja_DB
            DB.fundafarBelanjaDAO().delete(dtBelanja)

            // Refresh daftarBelanja list
            val daftarBelanja = DB.fundafarBelanjaDAO().selectAll()
            withContext(Dispatchers.Main) {
                adapterDaftar.isiData(daftarBelanja)

                // Refresh the history list
                val historyList = DB.historyBarangDAO().getAllHistory()
                historyAdapter.isiData(historyList)
            }
        }
    }
}