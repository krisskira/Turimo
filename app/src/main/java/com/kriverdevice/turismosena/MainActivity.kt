package com.kriverdevice.turismosena

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.kriverdevice.turismosena.application.Constants
import com.kriverdevice.turismosena.ui.main.SectionsPagerAdapter
import com.kriverdevice.turismosena.ui.main.modules.TurismoObjectList
import com.kriverdevice.turismosena.ui.main.modules.shared.TurismoObject
import org.json.JSONArray
import org.json.JSONObject

class MainActivity() : AppCompatActivity(), ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {
        //
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        //
    }

    override fun onPageSelected(position: Int) {
        //
    }

    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    val fragments: ArrayList<Fragment> = ArrayList()

    val sitios = TurismoObjectList()
    val operadores = TurismoObjectList()
    val hoteles = TurismoObjectList()

    var sitesList = ArrayList<TurismoObject>()
    var hotelsList = ArrayList<TurismoObject>()
    var operatorsList = ArrayList<TurismoObject>()

    lateinit var mRequestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRequestQueue = Volley.newRequestQueue(this)

        fragments.add(sitios)
        fragments.add(hoteles)
        fragments.add(operadores)

        val sectionsPagerAdapter =
            SectionsPagerAdapter(this, supportFragmentManager, fragments)

        viewPager = findViewById(R.id.view_pager)
        viewPager!!.adapter = sectionsPagerAdapter
        viewPager!!.currentItem = 1
        //viewPager!!.offscreenPageLimit = 1
        viewPager!!.addOnPageChangeListener(this)

        tabs = findViewById(R.id.tabs)
        tabs!!.setupWithViewPager(viewPager)

        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view -> loadAllData() }
        loadAllData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }


    private fun loadAllData(){

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, Constants.ALL_DATA, null,
            Response.Listener { response ->
                val sitesArray = response.getJSONArray("sites")
                val hotelsArray = response.getJSONArray("hotels")
                val operatorsArray = response.getJSONArray("operators")

                sitesList = TurismoObject.mapArray(sitesArray)
                hotelsList = TurismoObject.mapArray(hotelsArray)
                operatorsList = TurismoObject.mapArray(operatorsArray)

                hoteles.setData(hotelsList).refreshList()
                operadores.setData(operatorsList)//.refreshList()
                sitios.setData(sitesList)//.refreshList()

                Log.i("***->Len ", "" + operatorsList.count() )
            },
            Response.ErrorListener { error ->
                Snackbar.make(
                    findViewById(R.id.coordinatorlayout),
                    "Ups! Fallo en la conexion. (${error.networkResponse.statusCode})",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Reintentar", View.OnClickListener { loadAllData() })
                    .show()
            }
        )

        mRequestQueue.add(jsonObjectRequest)
    }



}