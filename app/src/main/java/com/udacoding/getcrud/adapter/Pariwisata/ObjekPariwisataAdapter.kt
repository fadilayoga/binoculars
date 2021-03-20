package com.udacoding.getcrud.adapter.Pariwisata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udacoding.getcrud.R
import com.udacoding.getcrud.model.Pariwisata.objek.DataItemObjekPariwisata
import kotlinx.android.synthetic.main.item_objek_pariwisata.view.*

class ObjekPariwisataAdapter(
    var data: MutableList<DataItemObjekPariwisata>?,
    var itemClick: OnItemClickListener
) : RecyclerView.Adapter<ObjekPariwisataAdapter.ObjekHolder>() {

    class ObjekHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.imageView_destinasi
        val judul = itemView.tv_nama_destinasi
        val lokasi = itemView.tv_lokasi_destinasi
    }

    interface OnItemClickListener {
        fun detail(item: DataItemObjekPariwisata?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjekHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_objek_pariwisata, parent, false)

        val holder = ObjekHolder(view)

        return holder
    }

    override fun onBindViewHolder(holder: ObjekHolder, position: Int) {
        val item = data?.get(position)

        //glide
        Glide.with(holder.itemView.context)
            .load(item?.Image)
            .error(R.drawable.no_image)
            .into(holder.image)

        holder.judul.text = item?.Nama

        holder.lokasi.text = item?.Lokasi

        holder.itemView.setOnClickListener {
            itemClick.detail(item)
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun updateList(tempList: MutableList<DataItemObjekPariwisata>) {
        data = tempList
        notifyDataSetChanged()
    }

}