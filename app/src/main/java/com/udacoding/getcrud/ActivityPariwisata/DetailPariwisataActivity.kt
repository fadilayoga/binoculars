package com.udacoding.getcrud.ActivityPariwisata

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.udacoding.getcrud.R
import com.udacoding.getcrud.model.Pariwisata.objek.DataItemObjekPariwisata
import kotlinx.android.synthetic.main.activity_detail_pariwisata.*

class DetailPariwisataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pariwisata)

        if (isConnected()) {
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

    private fun isConnected(): Boolean {
        var status: Boolean
        val connectionManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        val isConnected = activeNetwork?.isConnectedOrConnecting == true
        if (isConnected) {
            status = true
        } else {
            status = false
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
        return status
    }
}