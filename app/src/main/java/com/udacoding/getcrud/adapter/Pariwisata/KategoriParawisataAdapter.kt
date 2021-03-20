package com.udacoding.getcrud.adapter.Pariwisata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacoding.getcrud.R
import com.udacoding.getcrud.model.Pariwisata.kategori.DataItemKategoriPariwisata
import kotlinx.android.synthetic.main.item_kategori_pariwisata.view.*

class KategoriParawisataAdapter(
    var data: MutableList<DataItemKategoriPariwisata>?,
    var itemClick: OnClickListener
) : RecyclerView.Adapter<KategoriParawisataAdapter.KategoriHolder>() {

    class KategoriHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var category_list = itemView.btn_list_kategori_pariwisata_button

    }

    interface OnClickListener {
        fun listDataByKategory(item: DataItemKategoriPariwisata?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kategori_pariwisata, parent, false)

        val holder = KategoriHolder(view)

        return holder
    }

    override fun onBindViewHolder(holder: KategoriHolder, position: Int) {
        val item = data?.get(position)

        holder.category_list.setText(item?.kategori)

        holder.itemView.setOnClickListener {
            itemClick.listDataByKategory(item)
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }
}