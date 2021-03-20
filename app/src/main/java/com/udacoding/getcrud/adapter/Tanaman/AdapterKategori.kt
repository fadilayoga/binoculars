package com.udacoding.getcrud.adapter.Tanaman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacoding.getcrud.R
import com.udacoding.getcrud.model.Tanaman.kategori.DataItemKategori
import kotlinx.android.synthetic.main.item_kategori.view.*

class AdapterKategori(var data: MutableList<DataItemKategori>?, val itemClick : OnClickListener) :RecyclerView.Adapter<AdapterKategori.KategoriHolder>(){

    class KategoriHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val judul = itemView.tv_judul
        val btndelete = itemView.btn_delete
        val btnupdate = itemView.btn_update

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kategori, parent, false)

        val holder = KategoriHolder(view)

        return holder
    }

    override fun onBindViewHolder(holder: KategoriHolder, position: Int) {
        val item = data?.get(position)

        holder.judul.text = item?.kategori

        holder.btndelete.setOnClickListener {
            itemClick.delete(item)
        }
        holder.btnupdate.setOnClickListener {
            itemClick.update(item)
        }
    }

    override fun getItemCount(): Int {
        return data?.size?: 0
    }

    fun updateList(tempList: MutableList<DataItemKategori>) {
        data = tempList
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun delete(item: DataItemKategori?)
        fun update(item: DataItemKategori?)
    }
}