package com.kriverdevice.turismosena

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.kriverdevice.turismosena.ui.main.SectionsPagerAdapter
import com.kriverdevice.turismosena.ui.main.modules.Hoteles
import com.kriverdevice.turismosena.ui.main.modules.Operadores
import com.kriverdevice.turismosena.ui.main.modules.Sitios
import com.kriverdevice.turismosena.ui.main.modules.shared.TurismoObject

class MainActivity : AppCompatActivity(){

    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sitios = Sitios()
        val operadores = Operadores()
        val hoteles = Hoteles()

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
        }
    }

    fun getAllData() {

    }

    fun saveData(turismoObj: TurismoObject, module: Modules, action: Actions) {
        if (action == Actions.ADD) {
        }
    }

    enum class Modules {
        SITES, HOTELS, OPERASTORS
    }

    enum class Actions {
        ADD, UPDATE, DELETE
    }

}