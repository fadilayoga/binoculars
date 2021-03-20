package com.udacoding.getcrud.ActivityPariwisata

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.udacoding.getcrud.R
import kotlinx.android.synthetic.main.activity_pariwisata.*

class PariwisataActivity : AppCompatActivity() {

    var flag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pariwisata)

        first.setOnClickListener {
            flag = false
            var intent = Intent(this, ActivityKategoriPariwisata::class.java)
            startActivity(intent)
        }

        second.setOnClickListener {
            flag = false
            var intent = Intent(this, ActivityObjekPariwisata::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (flag == false) {
            isConnected()
            flag = true
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