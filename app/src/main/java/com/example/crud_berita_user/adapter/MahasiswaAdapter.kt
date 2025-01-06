package com.example.crud_berita_user.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crud_berita_user.DetailMhs
import com.example.crud_berita_user.R
import com.example.crud_berita_user.model.ModelMahasiswa

class MahasiswaAdapter (

    private val onClick: (ModelMahasiswa) -> Unit
) : ListAdapter<ModelMahasiswa, MahasiswaAdapter.MahasiwaViewHolder>(MahasiswaCallBack) {

    class MahasiwaViewHolder(itemView: View, val onClick: (ModelMahasiswa) -> Unit) :

        RecyclerView.ViewHolder(itemView) {
        private val username: TextView = itemView.findViewById(R.id.username)
        private val fullname: TextView = itemView.findViewById(R.id.fullName)
        private val email: TextView = itemView.findViewById(R.id.email)
        private val tglDaftar: TextView = itemView.findViewById(R.id.tanggalDaftar)
        val cardMahasiswa: CardView = itemView.findViewById(R.id.cardMahasiswa)


        //cek produk yang saaat ini

        private var currentProduct: ModelMahasiswa? = null

        init {
            itemView.setOnClickListener() {
                currentProduct?.let {
                    onClick(it)
                }
            }
        }

        fun bind(mahasiswa: ModelMahasiswa) {
            currentProduct = mahasiswa
            //set ke widget
            username.text= mahasiswa.username
            fullname.text = mahasiswa.fullname
            email.text = mahasiswa.email
            tglDaftar.text = mahasiswa.tgl_daftar


//            //untum menampilkan gambar
//            Glide.with(itemView)
//                .load("http://192.168.211.66/beritadb/gambar_berita/" + berita.gambar_berita)
//                .centerCrop()
//                .into(imgBerita)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MahasiwaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_item_mahasiswa, parent, false
        )
        return MahasiwaViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MahasiwaViewHolder, position: Int) {
        val mahasiswa = getItem(position)
        holder.bind(mahasiswa)

        //detail

        holder.cardMahasiswa.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailMhs::class.java)
            intent.putExtra("username", mahasiswa.username)
            intent.putExtra("fullname", mahasiswa.fullname)
            intent.putExtra("email", mahasiswa.email)
            intent.putExtra("tgl_daftar", mahasiswa.tgl_daftar)

            holder.itemView.context.startActivity(intent)
        }
    }
}
object MahasiswaCallBack : DiffUtil.ItemCallback<ModelMahasiswa>(){
    override fun areItemsTheSame(oldItem: ModelMahasiswa, newItem: ModelMahasiswa): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ModelMahasiswa, newItem: ModelMahasiswa): Boolean {
        return oldItem.id == newItem.id
    }

}