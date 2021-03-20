package com.udacoding.getcrud.ActivityPariwisata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.udacoding.getcrud.R
import com.udacoding.getcrud.adapter.Pariwisata.ObjekPariwisataAdapter
import com.udacoding.getcrud.model.Pariwisata.kategori.DataItemKategoriPariwisata
import com.udacoding.getcrud.model.Pariwisata.objek.DataItemObjekPariwisata
import com.udacoding.getcrud.model.Pariwisata.objek.ResponseObjekPariwisata
import com.udacoding.getcrud.network.NetworkModule
import kotlinx.android.synthetic.main.activity_objek_pariwisata.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityObjekPariwisata : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objek_pariwisata)

        var get = intent.getParcelableExtra<DataItemKategoriPariwisata>("data")

        if (get?.kategori.isNullOrEmpty()){
            supportActionBar?.title = "All Data Destination"
        }else{
            supportActionBar?.title = get?.kategori
        }

        getDataObjekPariwisata(get?.id)
    }

    private fun getDataObjekPariwisata(get: String?) {
        val objek = NetworkModule.servicePariwisata().getDataObjekPariwisata(get?:"")
        objek.enqueue(object : Callback<ResponseObjekPariwisata> {
            override fun onResponse(
                call: Call<ResponseObjekPariwisata>,
                response: Response<ResponseObjekPariwisata>
            ) {
                var message = response.body()?.message
                var success = response.body()?.isSuccess

                if (success == false) {
                    Toast.makeText(applicationContext, "$message", Toast.LENGTH_SHORT).show()
                }else{
                    val item = response.body()?.data
                    rv_list_objek.adapter =
                        ObjekPariwisataAdapter(item as MutableList<DataItemObjekPariwisata>?, object : ObjekPariwisataAdapter.OnItemClickListener{
                            override fun detail(item: DataItemObjekPariwisata?) {
                                var intent = Intent(this@ActivityObjekPariwisata, DetailPariwisataActivity::class.java)
                                intent.putExtra("data", item)
                                startActivity(intent)
                            }
                        })
                }
            }

            override fun onFailure(call: Call<ResponseObjekPariwisata>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}