package com.kriverdevice.turismosena.ui.main.modules


import android.os.Bundle
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

class TurismoObjectList : Fragment(), Modules, RecyclerItemSelectedListener {

    var recycler: RecyclerView? = null
    var adapter: Adapter? = null
    var turismoObjects = ArrayList<TurismoObject>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_turismo_object_list, container, false)
        this.recycler = view.findViewById<RecyclerView>(R.id.shared_recycler_view);
        this.recycler?.layoutManager = LinearLayoutManager(this.context)

        if (savedInstanceState != null) {
            turismoObjects = savedInstanceState.getParcelableArrayList("BUNDLE")
        }

        this.adapter = Adapter(turismoObjects, this)
        this.recycler?.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()
        this.adapter?.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("BUNDLE", turismoObjects)
        super.onSaveInstanceState(outState)
    }

    override fun onItemSelected(turismoObject: TurismoObject) {
        Toast.makeText(this.context, "***-> Item Selected: " + turismoObject.id, Toast.LENGTH_LONG).show();
    }

    override fun refreshList() {
        this.adapter?.notifyDataSetChanged()
    }

    override fun setData(turismoObjects: ArrayList<TurismoObject>): Modules {
        this.turismoObjects.clear()
        this.turismoObjects.addAll(turismoObjects)
        return this
    }

}
