package com.udacoding.getcrud.ActivityTanaman

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.udacoding.getcrud.R
import com.udacoding.getcrud.model.Tanaman.item.DataItemTanaman
import kotlinx.android.synthetic.main.activity_detail_tanaman.*

class DetailTanamanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tanaman)

        if (isConnection()) {
            var data = intent.getParcelableExtra<DataItemTanaman>("data")

            Glide.with(this)
                .load(data?.image)
                .error(R.drawable.no_image)
                .into(iv_detail_tanaman)
            tv_title_detail_tanaman.text = data?.nama
            desc_detail_tanaman.text = data?.deskripsi
            tv_detail_kategori_tanaman.text = "Kategori : ${data?.kategori}"
            tv_detail_harga_tanaman.text = "Harga : Rp.${data?.harga}"
            supportActionBar?.title = data?.nama
        }
    }

    private fun isConnection(): Boolean {
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