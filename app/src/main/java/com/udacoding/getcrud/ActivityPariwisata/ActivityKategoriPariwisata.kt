package com.udacoding.getcrud.ActivityPariwisata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.udacoding.getcrud.R
import com.udacoding.getcrud.adapter.Pariwisata.KategoriParawisataAdapter
import com.udacoding.getcrud.model.Pariwisata.kategori.DataItemKategoriPariwisata
import com.udacoding.getcrud.model.Pariwisata.kategori.ResponseKategoriPariwisata
import com.udacoding.getcrud.network.NetworkModule
import kotlinx.android.synthetic.main.activity_kategori_pariwisata.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityKategoriPariwisata : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori_pariwisata)

        supportActionBar?.title = "List Kategori Destinasi"

        getDataKategori()
    }

    private fun getDataKategori() {
        val kategoriList = NetworkModule.servicePariwisata().getDataKategoriPariwisata()
        kategoriList.enqueue(object : Callback<ResponseKategoriPariwisata> {
            override fun onResponse(
                call: Call<ResponseKategoriPariwisata>,
                response: Response<ResponseKategoriPariwisata>
            ) {
                if (response.isSuccessful) {
                    var item = response.body()?.data
                    rv_list_pariwisata_category.adapter =
                        KategoriParawisataAdapter(
                            item as MutableList<DataItemKategoriPariwisata>?,
                            object : KategoriParawisataAdapter.OnClickListener {
                                override fun listDataByKategory(item: DataItemKategoriPariwisata?) {
                                    var intent = Intent(this@ActivityKategoriPariwisata, ActivityObjekPariwisata::class.java)
                                    intent.putExtra("data", item)
                                    startActivity(intent)
                                }
                            })
                }
            }

            override fun onFailure(call: Call<ResponseKategoriPariwisata>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}