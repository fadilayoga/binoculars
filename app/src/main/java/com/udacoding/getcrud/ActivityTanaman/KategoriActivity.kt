package com.udacoding.getcrud.ActivityTanaman

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.udacoding.getcrud.R
import com.udacoding.getcrud.adapter.Tanaman.AdapterKategori
import com.udacoding.getcrud.model.Tanaman.action.ResponseAction
import com.udacoding.getcrud.model.Tanaman.kategori.DataItemKategori
import com.udacoding.getcrud.model.Tanaman.kategori.ResponseKategori
import com.udacoding.getcrud.network.NetworkModule
import kotlinx.android.synthetic.main.activity_kategori.*
import kotlinx.android.synthetic.main.activity_kategori.spin_kit
import kotlinx.android.synthetic.main.activity_list_data_tanaman.*
import kotlinx.android.synthetic.main.dialog_kategori.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KategoriActivity : AppCompatActivity() {

    lateinit var itemList: MutableList<DataItemKategori>
    lateinit var adapter: AdapterKategori

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori)

        supportActionBar?.title = "Kategori Tanaman"

        if (Connection()) {

            search_kategori.addTextChangedListener(object : TextWatcher {
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

            fab.setOnClickListener {
                Dialog(this@KategoriActivity).apply {
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    setCancelable(true)
                    setContentView(R.layout.dialog_kategori)

                    //setText
                    btnupdatedialog.setText("Insert")
                    tv_formdialogkategori.text = "Nama Kategori"

                    btnupdatedialog.setOnClickListener {
                        visibilitycall("off")
                        val dataInsert = et_dialogkategori.text.toString()
                        insertKategori(dataInsert)
                        dismiss()
                    }
                }.show()
            }

            //showdatakategori
            showData()
        }

    }

    private fun Connection(): Boolean {
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
        val tempList: MutableList<DataItemKategori> = ArrayList()
        for (d in itemList) {
            if (filterItem in d.kategori.toString().toLowerCase()
            ) {
                tempList.add(d)
            }
        }
        adapter.updateList(tempList)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showData() {
        val listKategori = NetworkModule.service().getDataKategori()
        listKategori.enqueue(object : Callback<ResponseKategori> {
            override fun onResponse(
                call: Call<ResponseKategori>,
                response: Response<ResponseKategori>
            ) {
                if (response.isSuccessful) {

                    itemList = (response.body()?.data ?: 0) as MutableList<DataItemKategori>

                    adapter = AdapterKategori(itemList, object : AdapterKategori.OnClickListener {

                        override fun delete(item: DataItemKategori?) {
                            AlertDialog.Builder(this@KategoriActivity).apply {
                                setTitle("Hapus Data")
                                setMessage("Yakin mau hapus data ?")
                                setPositiveButton("Hapus") { dialog, which ->
                                    visibilitycall("off")
                                    hapusData(item?.kategori)
                                    dialog.dismiss()
                                }
                                setNegativeButton("Batal") { dialog, which ->
                                    dialog.dismiss()
                                }
                            }.show()
                        }

                        override fun update(item: DataItemKategori?) {
                            Dialog(this@KategoriActivity).apply {
                                requestWindowFeature(Window.FEATURE_NO_TITLE)
                                setCancelable(true)
                                setContentView(R.layout.dialog_kategori)

                                //set hint
                                et_dialogkategori.setHint(item?.kategori)

                                btnupdatedialog.setOnClickListener {
                                    visibilitycall("off")
                                    updateData(
                                        et_dialogkategori.text.toString(),
                                        item?.kategori
                                    )
                                    dismiss()
                                }
                            }.show()
                        }
                    })

                    list_kategori.adapter = adapter

                    visibilitycall("on")

                }
            }

            override fun onFailure(call: Call<ResponseKategori>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun insertKategori(dataInsert: String) {
        val insertKategori = NetworkModule.service().insertkategori(dataInsert ?: "")
        insertKategori.enqueue(object : Callback<ResponseAction> {
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                val status = response.body()?.message
                val success = response.body()?.isSuccess
                if (success == false) {
                    Toast.makeText(this@KategoriActivity, "$status", Toast.LENGTH_SHORT).show()
                    visibilitycall("on")
                } else {
                    Toast.makeText(this@KategoriActivity, "$status", Toast.LENGTH_SHORT).show()
                    showData()
                }
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun updateData(kategori: String?, kategori_old: String?) {
        val update = NetworkModule.service().updatekategori(kategori ?: "", kategori_old ?: "")
        update.enqueue(object : Callback<ResponseAction> {
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                val status = response.body()?.message
                val success = response.body()?.isSuccess
                if (success == false) {
                    Toast.makeText(this@KategoriActivity, "$status", Toast.LENGTH_SHORT).show()
                    visibilitycall("on")
                } else {
                    Toast.makeText(this@KategoriActivity, "$status", Toast.LENGTH_SHORT).show()
                    showData()
                }
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun hapusData(kategori: String?) {
        val hapus = NetworkModule.service().deletekategori(kategori ?: "")
        hapus.enqueue(object : Callback<ResponseAction> {
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                val status = response.body()?.message
                Toast.makeText(this@KategoriActivity, "$status", Toast.LENGTH_SHORT).show()
                showData()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun visibilitycall(status: String) {
        if (status == "off") {
            fab.visibility = View.GONE
            list_kategori.visibility = View.GONE
            search_kategori.visibility = View.GONE
            spin_kit.visibility = View.VISIBLE
        } else {
            fab.visibility = View.VISIBLE
            list_kategori.visibility = View.VISIBLE
            search_kategori.visibility = View.VISIBLE
            spin_kit.visibility = View.GONE
        }
    }

}