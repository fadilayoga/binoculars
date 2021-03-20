package com.udacoding.getcrud.ActivityTanaman

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacoding.getcrud.R
import com.udacoding.getcrud.adapter.Tanaman.AdapterItem
import com.udacoding.getcrud.model.Tanaman.action.ResponseAction
import com.udacoding.getcrud.model.Tanaman.item.DataItemTanaman
import com.udacoding.getcrud.model.Tanaman.item.ResponseTanaman
import com.udacoding.getcrud.model.Tanaman.kategori.DataItemKategori
import com.udacoding.getcrud.model.Tanaman.kategori.ResponseKategori
import com.udacoding.getcrud.network.NetworkModule
import kotlinx.android.synthetic.main.activity_list_data_tanaman.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListDataTanaman : AppCompatActivity() {

    var currentSelected: String? = null
    var flag: Boolean = true
    var gridLayoutManaer: GridLayoutManager? = null
    var adapter: AdapterItem? = null
    var itemList: MutableList<String> = ArrayList()
    lateinit var listKategori: MutableList<DataItemKategori>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data_tanaman)

        supportActionBar?.title = "Welcome"

        //dropdown kategori
        showListKategori()

        //spinner
        spinnerCall()

        //fab
        fab_tanaman.setOnClickListener {
            flag = false
            val intent = Intent(this, InputTanamanActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_kategori, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_kategori -> {
                flag = false
                startActivity(Intent(this, KategoriActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (flag == false) {
            visibilitycall("off")

            //clear list
            itemList.clear()

            //spinner
            spinnerCall()

            //dropdown kategori
            showListKategori()

            flag = true
        }
    }

    private fun showListTanaman(items: String?) {
        val listTanaman = NetworkModule.service().getDataTanaman(items ?: "")
        listTanaman.enqueue(object : Callback<ResponseTanaman> {
            override fun onResponse(
                call: Call<ResponseTanaman>,
                response: Response<ResponseTanaman>
            ) {
                //recyclergrid
                gridLayoutManaer =
                    GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
                list_item_tanaman.layoutManager = gridLayoutManaer
                list_item_tanaman.setHasFixedSize(true)

                val item = response.body()?.data
                val message = response.body()?.message

                if (message != "Data ditemukan") {
                    Toast.makeText(applicationContext, "$message", Toast.LENGTH_SHORT).show()
                }

                adapter = AdapterItem(
                    item as MutableList<DataItemTanaman>?,
                    object : AdapterItem.OnClickListener {
                        override fun detail(item: DataItemTanaman?) {
                            var intent = Intent(this@ListDataTanaman, DetailTanamanActivity::class.java)
                            intent.putExtra("data", item)
                            startActivity(intent)
                        }

                        override fun delete(item: DataItemTanaman?) {
                            AlertDialog.Builder(this@ListDataTanaman).apply {
                                setTitle("Hapus Data")
                                setMessage("Yakin mau hapus data ?")
                                setPositiveButton("Hapus") { dialog, which ->
                                    visibilitycall("custom")
                                    hapusData(item?.id)
                                    dialog.dismiss()
                                }
                                setNegativeButton("Batal") { dialog, which ->
                                    dialog.dismiss()
                                }
                            }.show()
                        }

                        override fun update(item: DataItemTanaman?) {
                            flag = false
                            val intent =
                                Intent(applicationContext, InputTanamanActivity::class.java)
                            intent.putExtra("data", item)
                            startActivity(intent)
                        }
                    })
                list_item_tanaman.adapter = adapter

                //visibility
                visibilitycall("on")
            }

            override fun onFailure(call: Call<ResponseTanaman>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun hapusData(id: String?) {
        val deleteItem = NetworkModule.service().deleteItem(id ?: "")
        deleteItem.enqueue(object : Callback<ResponseAction> {
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                val status = response.body()?.message
                Toast.makeText(applicationContext, "$status", Toast.LENGTH_SHORT).show()

                //refresh
                if (currentSelected == "All Data") {
                    showListTanaman("")
                } else {
                    showListTanaman(currentSelected)
                }

            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
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
                    this@ListDataTanaman,
                    android.R.layout.simple_spinner_item,
                    itemList
                )
                spinner.adapter = arrayAdapter

                spinner.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<ResponseKategori>, t: Throwable) {
//                TODO("Not yet implemented")
            }
        })
    }

    private fun dataKategori() {
        itemList.add("All Data")
        for (d in listKategori) {
            itemList.add(d.kategori.toString())
        }
    }

    private fun visibilitycall(status: String) {
        if (status == "off") {
            list_item_tanaman.visibility = View.GONE
            spinner.visibility = View.GONE
            fab_tanaman.visibility = View.GONE
            spin_kit.visibility = View.VISIBLE
        } else if (status == "custom") {
            spin_kit.visibility = View.VISIBLE
            list_item_tanaman.visibility = View.GONE
        } else {
            list_item_tanaman.visibility = View.VISIBLE
            spinner.visibility = View.VISIBLE
            fab_tanaman.visibility = View.VISIBLE
            spin_kit.visibility = View.GONE
        }
    }

    private fun spinnerCall() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                visibilitycall("custom")

                val items = parent?.getItemAtPosition(position) as String
                currentSelected = items

                if (items == "All Data") {
                    showListTanaman("")
                } else {
                    showListTanaman(currentSelected)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}