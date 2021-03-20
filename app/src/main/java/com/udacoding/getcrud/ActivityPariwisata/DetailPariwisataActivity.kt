package com.udacoding.getcrud.ActivityPariwisata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.udacoding.getcrud.R
import com.udacoding.getcrud.model.Pariwisata.objek.DataItemObjekPariwisata
import kotlinx.android.synthetic.main.activity_detail_pariwisata.*

class DetailPariwisataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pariwisata)

        var data = intent.getParcelableExtra<DataItemObjekPariwisata>("data")
        Glide.with(this)
            .load(data?.Image)
            .error(R.drawable.no_image)
            .into(iv_detail_pariwisata)
        tv_title_detail_pariwisata.text = data?.Nama
        tv_lokasi_detail_pariwisata.text = data?.Lokasi
        desc_detail_pariwisata.text = data?.Deskripsi
        supportActionBar?.title = data?.Nama
    }
}