package com.example.crud_berita_user.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crud_berita_user.R
import com.example.crud_berita_user.model.ModelBerita

class BeritaAdapter (
    private val onClick : (ModelBerita) -> Unit
) : ListAdapter<ModelBerita, BeritaAdapter.BeritaViewHolder>(BeritaCallBack) {

    class BeritaViewHolder (itemView: View, val onClick: (ModelBerita) -> Unit) :
        RecyclerView.ViewHolder(itemView)
    {
        private val imgBerita: ImageView = itemView.findViewById(R.id.imgBerita)
        private val judulBerita: TextView = itemView.findViewById(R.id.judulBerita)
        private val isiBerita: TextView = itemView.findViewById(R.id.isiBerita)
        private val tglBerita: TextView = itemView.findViewById(R.id.tglBerita)

        //cek produk yg ada saat ini
        private var currentBerita: ModelBerita? = null

        init {
            itemView.setOnClickListener(){
                currentBerita?.let {
                    onClick(it)
                }
            }
        }
        fun bind(berita: ModelBerita){
            currentBerita = berita
            //set data ke widget
            judulBerita.text = berita.judul
            isiBerita.text = berita.isi_berita
            tglBerita.text = berita.tgl_berita

            //untuk menampilkan gambar pada widget (fetching gambar dengan glide)
            Glide.with(itemView).load("http://192.168.1.15/beritaDb/gambar_berita/"
                    + berita.gambar_berita).centerCrop()
                .into(imgBerita)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeritaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_berita, parent, false)
        return BeritaViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: BeritaViewHolder, position: Int) {
        val produk = getItem(position)
        holder.bind(produk)

    }

}

object BeritaCallBack : DiffUtil.ItemCallback<ModelBerita>() {
    override fun areItemsTheSame(oldItem: ModelBerita, newItem: ModelBerita): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ModelBerita, newItem: ModelBerita): Boolean {
        return oldItem == newItem
    }

}