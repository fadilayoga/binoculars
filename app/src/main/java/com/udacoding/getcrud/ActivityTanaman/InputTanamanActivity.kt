package com.udacoding.getcrud.ActivityTanaman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.udacoding.getcrud.R
import com.udacoding.getcrud.model.Tanaman.action.ResponseAction
import com.udacoding.getcrud.model.Tanaman.item.DataItemTanaman
import com.udacoding.getcrud.model.Tanaman.kategori.DataItemKategori
import com.udacoding.getcrud.model.Tanaman.kategori.ResponseKategori
import com.udacoding.getcrud.network.NetworkModule
import kotlinx.android.synthetic.main.activity_input_tanaman.*
import kotlinx.android.synthetic.main.activity_list_data_tanaman.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputTanamanActivity : AppCompatActivity() {

    var currentKategori: String? = null
    var itemList: MutableList<String> = ArrayList()
    lateinit var listKategori: MutableList<DataItemKategori>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_tanaman)

        supportActionBar?.title = "Insert Data"

        val getData = intent.getParcelableExtra<DataItemTanaman>("data")
        if (getData != null) {
            et_nama.setText(getData.nama)
            et_deskripsi.setText(getData.deskripsi)
            et_image.setText(getData.image)
            et_harga.setText(getData.harga)
            currentKategori = getData.kategori
            btn_input_data.text = "Update"
            supportActionBar?.title = "Update Data"
        }

        when (btn_input_data.text) {
            "Update" -> {
                btn_input_data.setOnClickListener {
                    UpdateDataItem(
                        getData?.id,
                        et_nama.text.toString(),
                        et_deskripsi.text.toString(),
                        et_image.text.toString(),
                        et_harga.text.toString(),
                        currentKategori
                    )
                }
            }
            else -> {
                btn_input_data.setOnClickListener {
                    InsertData(
                        getData?.id,
                        et_nama.text.toString(),
                        et_deskripsi.text.toString(),
                        et_image.text.toString(),
                        et_harga.text.toString(),
                        currentKategori
                    )
                }
            }
        }
        showListKategori()

        //spinner
        spinnerCall()
    }

    private fun InsertData(
        id: String?,
        nama: String?,
        deskripsi: String?,
        image: String?,
        harga: String?,
        kategori: String?
    ) {
        val insert = NetworkModule.service().insertTanaman(
            id ?: "",
            nama ?: "",
            deskripsi ?: "",
            image ?: "",
            harga ?: "",
            kategori ?: ""
        )
        insert.enqueue(object : Callback<ResponseAction> {
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                var respon = response.body()?.isSuccess
                if (respon == false) {
                    Toast.makeText(
                        applicationContext,
                        "${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun UpdateDataItem(
        id: String?,
        nama: String?,
        deskripsi: String?,
        image: String?,
        harga: String?,
        kategori: String?
    ) {

        val update = NetworkModule.service().updateTanaman(
            id ?: "",
            nama ?: "",
            deskripsi ?: "",
            image ?: "",
            harga ?: "",
            kategori ?: ""
        )
        update.enqueue(object : Callback<ResponseAction> {
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                var respon = response.body()?.isSuccess
                if (respon == false) {
                    Toast.makeText(
                        applicationContext,
                        "${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun spinnerCall() {
        spinner_input.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val items = parent?.getItemAtPosition(position) as String
                currentKategori = items
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun showListKategori() {
        val listkategori = NetworkModule.service().getDataKategori()
        listkategori.enqueue(object : Callback<ResponseKategori> {
            override fun onResponse(
                call: Call<ResponseKategori>,
                response: Response<ResponseKategori>
            ) {
                listKategori = (response.body()?.data ?: 0) as MutableList<DataItemKategori>
                dataKategori()
                val arrayAdapter = ArrayAdapter(
                    this@InputTanamanActivity,
                    android.R.layout.simple_spinner_item,
                    itemList
                )
                spinner_input.adapter = arrayAdapter
                spinner_input.setSelection(itemList.indexOf(currentKategori))
            }

            override fun onFailure(call: Call<ResponseKategori>, t: Throwable) {
//                TODO("Not yet implemented")
            }
        })
    }

    private fun dataKategori() {
        for (d in listKategori) {
            itemList.add(d.kategori.toString())
        }
    }
}