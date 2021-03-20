package com.udacoding.getcrud.ActivityPariwisata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.udacoding.getcrud.R
import kotlinx.android.synthetic.main.activity_pariwisata.*

class PariwisataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pariwisata)

        first.setOnClickListener {
            var intent = Intent(this, ActivityKategoriPariwisata::class.java)
            startActivity(intent)
        }

        second.setOnClickListener {
            var intent = Intent(this, ActivityObjekPariwisata::class.java)
            startActivity(intent)
        }
    }
}