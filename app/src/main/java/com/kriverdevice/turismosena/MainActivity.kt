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
import android.widget.Toast
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
import com.kriverdevice.turismosena.application.Constants.ACTIONS
import com.kriverdevice.turismosena.application.Constants.MODULES
import com.kriverdevice.turismosena.ui.main.ListTurismoItemsFragment
import com.kriverdevice.turismosena.ui.main.SectionsPagerAdapter
import com.kriverdevice.turismosena.ui.main.shared.TurismoItemListener
import com.kriverdevice.turismosena.ui.main.shared.TurismoObject
import org.json.JSONObject

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener, View.OnClickListener, TurismoItemListener,
    SearchView.OnQueryTextListener {

    private val TAG_LOG: String = "***-> MainActivity"

    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    var fab: FloatingActionButton? = null
    var progressIndicator: ProgressBar? = null
    var toolbar: Toolbar? = null

    var mRequestQueue: RequestQueue? = null

    val sitios = ListTurismoItemsFragment()
    val operadores = ListTurismoItemsFragment()
    val hoteles = ListTurismoItemsFragment()

    val fragments: ArrayList<ListTurismoItemsFragment> = ArrayList()
    var sitesList = ArrayList<TurismoObject>()
    var hotelsList = ArrayList<TurismoObject>()
    var operatorsList = ArrayList<TurismoObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
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

        setSupportActionBar(toolbar)
        tabs?.setupWithViewPager(viewPager)
        viewPager?.addOnPageChangeListener(this)
        fab?.setOnClickListener(this)
        sitios.turismoItemListener = this
        hoteles.turismoItemListener = this
        operadores.turismoItemListener = this

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
        outState.putParcelableArrayList(MODULES.sites.name, sitesList)
        outState.putParcelableArrayList(MODULES.hotels.name, hotelsList)
        outState.putParcelableArrayList(MODULES.operators.name, operatorsList)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        this.sitesList = savedInstanceState.getParcelableArrayList(MODULES.sites.name)
        this.hotelsList = savedInstanceState.getParcelableArrayList(MODULES.hotels.name)
        this.operatorsList = savedInstanceState.getParcelableArrayList(MODULES.operators.name)

        this.sitios.setData(sitesList)
        this.hoteles.setData(hotelsList)
        this.operadores.setData(operatorsList)

        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        Log.d(TAG_LOG, "Filtro parcial: " + p0)
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

    override fun onItemSelected(turismoObject: TurismoObject) {
        val moduleSelected = when (viewPager?.currentItem) {
            0 -> getString(R.string.tab_text_sitios)
            1 -> getString(R.string.tab_text_hoteles)
            else -> getString(R.string.tab_text_operadores)
        }

        val i = Intent(this, FormActivity::class.java).apply {
            putExtra("ACTION", ACTIONS.UPDATE.name)
            putExtra("MODULE", moduleSelected)
            putExtra("DATA", turismoObject)
        }
        startActivityForResult(i, Constants.FORM)
    }

    override fun onClick(p0: View?) {

        when (p0?.id) {

            R.id.fab -> {
                val moduleSelected = when (viewPager?.currentItem) {
                    0 -> getString(R.string.tab_text_sitios)
                    1 -> getString(R.string.tab_text_hoteles)
                    else -> getString(R.string.tab_text_operadores)
                }

                val i = Intent(this, FormActivity::class.java).apply {
                    putExtra("ACTION", ACTIONS.ADD.name)
                    putExtra("MODULE", moduleSelected)
                }
                startActivityForResult(i, Constants.FORM)
            }
            // retry action from snack bar
            else -> {
                loadAllData()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG_LOG, "ActivityResult " + requestCode + " ResultCode: " + resultCode)
        //finishActivity(requestCode)
        /*val p = ProgressDialog(this)
            p.setTitle("Progress")
        p.show()*/
        // progressBar.visibility = View.VISIBLE
    }

    override fun onPageSelected(position: Int) {
        fragments[position].refreshList()
    }

    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

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
                    .setAction("Reintentar", this)
                    .show()
            }
        )
        mRequestQueue?.add(jsonObjectRequest)
    }

    private fun setDataList(response: JSONObject) {

        val sitesArray = response.getJSONArray(MODULES.sites.name)
        val hotelsArray = response.getJSONArray(MODULES.hotels.name)
        val operatorsArray = response.getJSONArray(MODULES.operators.name)

        sitesList = TurismoObject.mapArray(sitesArray)
        hotelsList = TurismoObject.mapArray(hotelsArray)
        operatorsList = TurismoObject.mapArray(operatorsArray)

        sitios.setData(sitesList).refreshList()
        hoteles.setData(hotelsList)
        operadores.setData(operatorsList)

        progressIndicator?.visibility = View.GONE
    }


    private fun sendData(method: Int, urlModule: String, turismoObject: TurismoObject) {
        // method = Request.Method.PUT o POST


        val jsonObjectRequest = JsonObjectRequest(
            method, urlModule, JSONObject(turismoObject.toString()),
            Response.Listener { response ->
                Toast.makeText(this, response.toString(4), Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                Snackbar.make(
                    findViewById(R.id.coordinatorlayout),
                    "Ups! Fallo en la conexion.",
                    Snackbar.LENGTH_INDEFINITE
                )
                    //.setAction("Reintentar", View.OnClickListener { loadAllData() })
                    .show()
            }
        )
        mRequestQueue?.add(jsonObjectRequest)
    }

}