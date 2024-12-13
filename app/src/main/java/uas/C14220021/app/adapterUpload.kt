package nit2x.paba.projectroom

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uas.C14220021.app.database.DataKesehatan

class AdapterDaftar(private val daftarBelanja: MutableList<DataKesehatan>) :


    RecyclerView.Adapter<AdapterDaftar.ListViewHolder>() {
    interface OnItemClickCallback {
        fun delData(dtBelanja: DataKesehatan)
        fun pindahHistory(dtBelanja: DataKesehatan)
    }
    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    fun isiData(daftar: List<DataKesehatan>) {
        daftarBelanja.clear()
        daftarBelanja.addAll(daftar)
        notifyDataSetChanged()
    }


    private lateinit var onItemClickCallback : OnItemClickCallback


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTanggal: TextView = itemView.findViewById<TextView>(R.id.tvTanggal)
        val tvItem: TextView = itemView.findViewById<TextView>(R.id.tvJam)
        val tvBerat: TextView = itemView.findViewById<TextView>(R.id.tvBeratbadan)
        val tvTekanan: TextView = itemView.findViewById<TextView>(R.id.tvTekanandarah)
        val tvCatatan: TextView = itemView.findViewById<TextView>(R.id.tvCatatan)

        val _btnUploadFirebase = itemView.findViewById<ImageButton>(R.id.buttonUpload)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_halaman_upload, parent, false)
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentItem = DataKesehatan[position]


        holder.tvTanggal.text = currentItem.tanggal
        holder.tvItem.text = currentItem.jam
        holder.tvBerat.text = currentItem.berat
        holder.tvTekanan.text = currentItem.tekanan
        holder.tvCatatan.text = currentItem.catatan


    }


    override fun getItemCount(): Int {
        return DataKesehatan.size
    }

}
