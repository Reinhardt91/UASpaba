package nit2x.paba.projectroom

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import nit2x.paba.projectroom.database.DataKesehatanDB
import nit2x.paba.projectroom.helper.DataHelper
import uas.C14220021.app.database.DataKesehatan

class HalamanUtama : AppCompatActivity() {
    var DB = DataKesehatanDB.getDatabase(this)
    private lateinit var _etTanggal: EditText
    private lateinit var _etJam: EditText
    private lateinit var _etBeratBadan: EditText
    private lateinit var _etTekananDarah: EditText
    private lateinit var _etCatatan: EditText
    var tanggal = DataHelper.getCurrentDate()

    var iID : Int = 0
    var iAddEdit : Int = 0

    private lateinit var btnTambah: Button
    private lateinit var btnUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_halaman_utama)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _etTanggal = findViewById<EditText>(R.id.tanggal)
        _etJam = findViewById<EditText>(R.id.jam)
        _etBeratBadan = findViewById<EditText>(R.id.beratBadan)
        _etTekananDarah = findViewById<EditText>(R.id.etTekanan)
        _etCatatan = findViewById<EditText>(R.id.etCatatan)

        btnTambah = findViewById<Button>(R.id.btnTambah)
        btnUpdate = findViewById<Button>(R.id.btnUpdate)
        iID = intent.getIntExtra("id", 0)
        iAddEdit = intent.getIntExtra("addEdit",0)

        if(iAddEdit == 0) {
            btnTambah.visibility= View.VISIBLE
            btnUpdate.visibility = View.GONE
            _etTanggal.isEnabled = true



        } else {
            btnTambah.visibility= View.GONE
            btnUpdate.visibility = View.VISIBLE
            _etTanggal.isEnabled = false

            CoroutineScope(Dispatchers.IO).async {
                val tanggal = DB.funDataKesehatanDAO().tanggal
                _etTanggal.setText(tanggal.tanggal)
                _etJumlah.setText(item.jumlah)
            }
        }
        btnTambah.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.funDataKesehatanDAO().insert(
                    DataKesehatan(
                        tanggal = tanggal,
                        jam = _etJam.text.toString(),
                        beratbadan = _etBeratBadan.text.toString(),
                        tekanandarah = _etTekananDarah.text.toString(),
                        catatan = _etCatatan.text.toString()
                    )
                )
            }
        }
        btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.funDataKesehatanDAO().update(
                    isi_tanggal = tanggal,
                    isi_jam = _etJam.text.toString(),
                    isi_berat = _etBeratBadan.text.toString(),
                    isi_tekanan =_etTekananDarah.text.toString(),
                    isi_catatan = _etCatatan.text.toString(),
                    pilihid = iID

                )
            }
        }
    }
}