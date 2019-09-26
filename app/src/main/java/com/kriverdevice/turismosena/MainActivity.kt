package com.kriverdevice.turismosena

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.kriverdevice.turismosena.application.Fail
import com.kriverdevice.turismosena.application.HttpRequest
import com.kriverdevice.turismosena.application.Result
import com.kriverdevice.turismosena.application.Success
import com.kriverdevice.turismosena.ui.main.SectionsPagerAdapter
import com.kriverdevice.turismosena.ui.main.modules.TurismoObjectList


class MainActivity : AppCompatActivity(), HttpRequest.OnHttpRequestComplete {

    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sitios = TurismoObjectList()
        val operadores = TurismoObjectList()
        val hoteles = TurismoObjectList()
        // Operadores()
        // Hoteles()

        fragments.add(sitios)
        fragments.add(operadores)
        fragments.add(hoteles)

        val sectionsPagerAdapter =
            SectionsPagerAdapter(this, supportFragmentManager, fragments)

        viewPager = findViewById(R.id.view_pager)
        viewPager!!.adapter = sectionsPagerAdapter

        tabs = findViewById(R.id.tabs)
        tabs!!.setupWithViewPager(viewPager)

        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            /*val currentTab = tabs!!.selectedTabPosition
            Toast.makeText(this, "Currente Tab: " + currentTab, Toast.LENGTH_LONG).show()
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            //HttpRequest("https://turismo-sena.herokuapp.com", this, null)
            /*
            val data = ArrayList<TurismoObject>()
            data.add(TurismoObject("1","Uno","","","","",""))
            sitios.setData(data).refreshList()
            */


            //RequestQueue initialized
            val mRequestQueue = Volley.newRequestQueue(this)

            //String Request initialized
            val mStringRequest = StringRequest(Request.Method.GET, "https://turismo-sena.herokuapp.com", object :
                Response.Listener<String> {
                override fun onResponse(response: String) {

                    Toast.makeText(applicationContext, "Response :$response", Toast.LENGTH_LONG)
                        .show()//display the response on screen

                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError) {

                    Log.i("***-> Error en voley", "Error :$error")
                }
            })

            mRequestQueue.add(mStringRequest)



        }
    }

    override fun onComplete(response: Result) {

        when (response) {
            is Success -> {
                val i = response.data
                /*var turismoObject = TurismoObject(i.getString("id"),"Nombre:",i.getString("direccion"), "Calle 1", "a", "363", "sds")
                turismoObjects.add(turismoObject)
                adapter.notifyDataSetChanged()*/
                Log.d("***->Request response: ", response.data)
            }
            is Fail -> {
                Log.d("***->Oops: ", response.error.toString())
            }

        }


    }

}