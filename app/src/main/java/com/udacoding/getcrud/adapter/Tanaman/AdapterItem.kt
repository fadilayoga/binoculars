package com.udacoding.getcrud.adapter.Tanamanimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport androidx.recyclerview.widget.RecyclerViewimport com.bumptech.glide.Glideimport com.udacoding.getcrud.Rimport com.udacoding.getcrud.model.Tanaman.item.DataItemTanamanimport kotlinx.android.synthetic.main.item_tanaman.view.*class AdapterItem(var data: MutableList<DataItemTanaman>?, var itemClick: OnClickListener) :    RecyclerView.Adapter<AdapterItem.ItemHolder>() {    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {        var title = itemView.tv_tanaman        var image = itemView.image_tanaman        var btn_update = itemView.btn_update_item        var btn_delete = itemView.btn_delete_item    }    interface OnClickListener {        fun detail(item: DataItemTanaman?)        fun delete(item: DataItemTanaman?)        fun update(item: DataItemTanaman?)    }    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tanaman, parent, false)        val holder = ItemHolder(view)        return holder    }    override fun onBindViewHolder(holder: ItemHolder, position: Int) {        val item = data?.get(position)        holder.title.text = item?.nama        //glide        Glide.with(holder.itemView.context)            .load(item?.image)            .error(R.drawable.no_image)            .into(holder.image)        holder.itemView.setOnClickListener {            itemClick.detail(item)        }        holder.btn_update.setOnClickListener {            itemClick.update(item)        }        holder.btn_delete.setOnClickListener {            itemClick.delete(item)        }    }    override fun getItemCount(): Int {        return data?.size ?: 0    }}