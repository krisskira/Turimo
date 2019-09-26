package com.kriverdevice.turismosena.ui.main.modules


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kriverdevice.turismosena.R
import com.kriverdevice.turismosena.ui.main.modules.shared.Adapter
import com.kriverdevice.turismosena.ui.main.modules.shared.Modules
import com.kriverdevice.turismosena.ui.main.modules.shared.RecyclerItemSelectedListener
import com.kriverdevice.turismosena.ui.main.modules.shared.TurismoObject


/**
 * A simple [Fragment] subclass.
 *
 */
class TurismoObjectList : Fragment(), Modules, RecyclerItemSelectedListener {

    lateinit var recycler: RecyclerView
    lateinit var adapter: Adapter
    var turismoObjects = ArrayList<TurismoObject>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_turismo_object_list, container, false)
        recycler = view.findViewById<RecyclerView>(R.id.shared_recycler_view);
        val layoutManager = LinearLayoutManager(this.context)
        recycler.layoutManager = layoutManager
        adapter = Adapter(turismoObjects, this)
        recycler.adapter = adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    override fun onItemSelected(turismoObject: TurismoObject) {
        Toast.makeText(this.context, "***-> Item Selected: " + turismoObject.name, Toast.LENGTH_LONG).show();
    }

    override fun refreshList() {
        adapter.notifyDataSetChanged()
        Log.d("***->", "Refrescando la lista: " + turismoObjects.count())
    }

    override fun setData(turismoObjects: ArrayList<TurismoObject>): Modules {
        Log.d("***-> SeData: ", "Enpujando el dato: " + turismoObjects.count())
        this.turismoObjects.addAll(turismoObjects)
        return this
    }

}
