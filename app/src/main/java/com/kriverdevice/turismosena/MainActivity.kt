package com.kriverdevice.turismosena

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
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
import com.kriverdevice.turismosena.application.Constants.hotelsKey
import com.kriverdevice.turismosena.application.Constants.operatorsKey
import com.kriverdevice.turismosena.application.Constants.sitesKey
import com.kriverdevice.turismosena.ui.main.SectionsPagerAdapter
import com.kriverdevice.turismosena.ui.main.modules.TurismoObjectList
import com.kriverdevice.turismosena.ui.main.modules.shared.TurismoObject
import org.json.JSONObject

class MainActivity() : AppCompatActivity(), ViewPager.OnPageChangeListener, View.OnClickListener, SearchView.OnQueryTextListener {

    val FLAG_LOG = "***-> MainActivity"

    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    var fab: FloatingActionButton? = null
    var progressIndicator: ProgressBar? = null
    var toolbar: Toolbar? = null

    var mRequestQueue: RequestQueue? = null

    val sitios = TurismoObjectList()
    val operadores = TurismoObjectList()
    val hoteles = TurismoObjectList()

    val fragments: ArrayList<TurismoObjectList> = ArrayList()
    var sitesList = ArrayList<TurismoObject>()
    var hotelsList = ArrayList<TurismoObject>()
    var operatorsList = ArrayList<TurismoObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        viewPager = findViewById(R.id.view_pager)
        tabs = findViewById(R.id.tabs)
        fab = findViewById(R.id.fab)
        progressIndicator = findViewById(R.id.progressBar)

        mRequestQueue = Volley.newRequestQueue(this)

        fragments.add(sitios)
        fragments.add(hoteles)
        fragments.add(operadores)

        viewPager?.adapter = SectionsPagerAdapter(this, supportFragmentManager, fragments as ArrayList<Fragment>)
        viewPager?.offscreenPageLimit = 3

        tabs?.setupWithViewPager(viewPager)

        viewPager?.addOnPageChangeListener(this)
        fab?.setOnClickListener(this)

        if (savedInstanceState == null) {
            loadAllData()
        }

        if (Build.VERSION.SDK_INT > 22) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_layout, menu)
        val menuItem = menu?.findItem(R.id.app_bar_search)
        val searchMenuView = menuItem?.actionView as SearchView
        searchMenuView.setOnQueryTextListener(this)
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(Constants.sitesKey, sitesList)
        outState.putParcelableArrayList(Constants.hotelsKey, hotelsList)
        outState.putParcelableArrayList(Constants.operatorsKey, operatorsList)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        this.sitesList = savedInstanceState.getParcelableArrayList(sitesKey)
        this.hotelsList = savedInstanceState.getParcelableArrayList(hotelsKey)
        this.operatorsList = savedInstanceState.getParcelableArrayList(operatorsKey)

        this.sitios.setData(sitesList)
        this.hoteles.setData(hotelsList)
        this.operadores.setData(operatorsList)

        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        Log.d(FLAG_LOG, "Filtro parcial: " + p0)
        when (viewPager?.currentItem) {
            0 -> {
                // Filtra para sitios
            }
            1 -> {
                // Filtra para hoteles
            }
            else -> {
                //Filtra para operadores
            }
        }
        return true
    }

    override fun onClick(p0: View?) {

        val moduleSelected = when (viewPager?.currentItem) {
            0 -> sitesKey
            1 -> hotelsKey
            else -> operatorsKey
        }

        val i = Intent(this, FormActivity::class.java).apply {
            putExtra("ACTION", "ADD")
            putExtra("MODULE", moduleSelected)
        }

        startActivityForResult(i, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*val p = ProgressDialog(this)
            p.setTitle("Progress")
        p.show()*/
        // progressBar.visibility = View.VISIBLE
    }

    override fun onPageSelected(position: Int) {
        fragments.get(position).refreshList()
    }

    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onQueryTextSubmit(p0: String?): Boolean { return true }

    private fun loadAllData() {

        progressIndicator?.visibility = View.VISIBLE

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, Constants.ALL_DATA, null,
            Response.Listener { response ->
                setDataList(response)
            },
            Response.ErrorListener { error ->
                Snackbar.make(
                    findViewById(R.id.coordinatorlayout),
                    "Ups! Fallo en la conexion.",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Reintentar", View.OnClickListener { loadAllData() })
                    .show()
            }
        )

        mRequestQueue?.add(jsonObjectRequest)
    }

    private fun setDataList(response: JSONObject) {

        val sitesArray = response.getJSONArray(sitesKey)
        val hotelsArray = response.getJSONArray(hotelsKey)
        val operatorsArray = response.getJSONArray(operatorsKey)

        sitesList = TurismoObject.mapArray(sitesArray)
        hotelsList = TurismoObject.mapArray(hotelsArray)
        operatorsList = TurismoObject.mapArray(operatorsArray)

        Log.d(FLAG_LOG, "Count Sites mapped: " + sitesList.count() )
        Log.d(FLAG_LOG, "Count Hotels mapped: " + hotelsList.count() )
        Log.d(FLAG_LOG, "Count Operators mapped: " + operatorsList.count() )

        sitios.setData(sitesList).refreshList()
        hoteles.setData(hotelsList)
        operadores.setData(operatorsList)

        progressIndicator?.visibility = View.GONE
    }

}