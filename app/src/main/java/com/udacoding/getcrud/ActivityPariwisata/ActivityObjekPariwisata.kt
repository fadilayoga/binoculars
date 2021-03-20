package com.udacoding.getcrud.ActivityPariwisata

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.udacoding.getcrud.R
import com.udacoding.getcrud.adapter.Pariwisata.ObjekPariwisataAdapter
import com.udacoding.getcrud.adapter.Tanaman.AdapterKategori
import com.udacoding.getcrud.model.Pariwisata.kategori.DataItemKategoriPariwisata
import com.udacoding.getcrud.model.Pariwisata.objek.DataItemObjekPariwisata
import com.udacoding.getcrud.model.Pariwisata.objek.ResponseObjekPariwisata
import com.udacoding.getcrud.model.Tanaman.kategori.DataItemKategori
import com.udacoding.getcrud.network.NetworkModule
import kotlinx.android.synthetic.main.activity_objek_pariwisata.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityObjekPariwisata : AppCompatActivity() {

    lateinit var itemList: MutableList<DataItemObjekPariwisata>
    lateinit var adapter: ObjekPariwisataAdapter
    var flag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objek_pariwisata)

        var get = intent.getParcelableExtra<DataItemKategoriPariwisata>("data")

        if (get?.kategori.isNullOrEmpty()) {
            supportActionBar?.title = "All Data Destination"
        } else {
            supportActionBar?.title = get?.kategori
        }

        if (isConnected()) {
            getDataObjekPariwisata(get?.id)

            search_objek_pariwisata.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    filterList(s.toString())
                }
            })
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

    private fun filterList(filterItem: String) {
        val tempList: MutableList<DataItemObjekPariwisata> = ArrayList()
        for (d in itemList) {
            if (filterItem in d.Nama.toString().toLowerCase()
            ) {
                tempList.add(d)
            }
        }
        adapter.updateList(tempList)
    }

    private fun getDataObjekPariwisata(get: String?) {
        val objek = NetworkModule.servicePariwisata().getDataObjekPariwisata(get ?: "")
        objek.enqueue(object : Callback<ResponseObjekPariwisata> {
            override fun onResponse(
                call: Call<ResponseObjekPariwisata>,
                response: Response<ResponseObjekPariwisata>
            ) {
                var message = response.body()?.message
                var success = response.body()?.isSuccess

                if (success == false) {
                    Toast.makeText(applicationContext, "$message", Toast.LENGTH_SHORT).show()
                } else {
                    itemList = response.body()?.data as MutableList<DataItemObjekPariwisata>
                    adapter = ObjekPariwisataAdapter(
                        itemList,
                        object : ObjekPariwisataAdapter.OnItemClickListener {
                            override fun detail(item: DataItemObjekPariwisata?) {
                                flag = false
                                var intent = Intent(
                                    this@ActivityObjekPariwisata,
                                    DetailPariwisataActivity::class.java
                                )
                                intent.putExtra("data", item)
                                startActivity(intent)
                            }
                        })
                    spin_kit_objek_pariwisata.visibility = View.GONE
                    rv_list_objek.adapter = adapter
                }
            }

            override fun onFailure(call: Call<ResponseObjekPariwisata>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}