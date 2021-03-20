package com.udacoding.getcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.udacoding.getcrud.ActivityPariwisata.PariwisataActivity
import com.udacoding.getcrud.ActivityTanaman.ListDataTanaman
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bride_tanaman.setOnClickListener {
            var intent = Intent(this, ListDataTanaman::class.java)
            startActivity(intent)
        }

        bridge_pariwisata.setOnClickListener {
            var intent = Intent(this, PariwisataActivity::class.java)
            startActivity(intent)
        }
    }
}